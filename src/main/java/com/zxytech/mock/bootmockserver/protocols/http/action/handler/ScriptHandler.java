package com.zxytech.mock.bootmockserver.protocols.http.action.handler;

import com.google.gson.Gson;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionHandler;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.HttpMockActionType;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.ScriptActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.ScriptTypeEnum;
import lombok.Data;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;

@HttpMockActionType("script")
public class ScriptHandler implements HttpMockActionHandler {
    public static final String SCRIPT_UPLOAD_DIR = "file:./scripts";
    private static final Logger logger = LoggerFactory.getLogger(ScriptHandler.class);
    private static final String PYTHON_HANDLER_FUNC_NAME = "mock_process";
    private static final PythonInterpreter PYTHON_INTERPRETER = new PythonInterpreter();

    private ScriptEngine scriptEngine;

    private Resource scriptsDir;

    public ScriptHandler() {
        ScriptEngineManager manager = new ScriptEngineManager();
        this.scriptEngine = manager.getEngineByExtension(".groovy");
        this.scriptsDir = new DefaultResourceLoader().getResource(SCRIPT_UPLOAD_DIR);
    }

    @Override
    public boolean process(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        AbstractActionEntity actionEntity)
        throws Exception {
        if (actionEntity instanceof ScriptActionEntity) {
            String scriptFilePath = ((ScriptActionEntity) actionEntity).getScriptPath();
            ScriptTypeEnum scriptType = ((ScriptActionEntity) actionEntity).getScriptType();

            logger.info(scriptFilePath);
            Path scriptPath =
                Paths.get(
                    scriptsDir.getFile().getAbsolutePath(),
                    scriptType.name().toLowerCase(),
                    scriptFilePath);
            assert scriptPath.endsWith(ScriptTypeEnum.GROOVY.getExtension())
                || scriptPath.endsWith(ScriptTypeEnum.PYTHON.getExtension());
            assert Files.exists(scriptPath);
            String script = new String(Files.readAllBytes(scriptPath));
            Object result;
            switch (scriptType) {
                case GROOVY:
                    Bindings bindings = scriptEngine.createBindings();
                    bindings.put("servletRequest", request);
                    bindings.put("servletResponse", response);

                    result = scriptEngine.eval(script, bindings);
                    break;

                case PYTHON:
                    result = executePythonScript(script, request);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType(((PyResult) result).getContentType());
                    response.setStatus(((PyResult) result).getStatusCode());
                    response.getWriter().write(((PyResult) result).getResponse());
                    return true;

                default:
                    throw new NoSuchMethodError("Only Groovy or Python script is Supported");
            }
            if (result != null) {
                logger.info(result.toString());
            }
        }
        return false;
    }

    private PyResult executePythonScript(String script, HttpServletRequest request)
        throws IOException, ServletException {
        PYTHON_INTERPRETER.exec(script);
        PyFunction mockProcessFunc = PYTHON_INTERPRETER.get(PYTHON_HANDLER_FUNC_NAME, PyFunction.class);
        PyDictionary pyRequest = new PyDictionary();

        pyRequest.put("method", request.getMethod());
        pyRequest.put("contentType", request.getContentType());
        pyRequest.put("requestUri", request.getRequestURI());
        pyRequest.put("contextPath", request.getContextPath());
        pyRequest.put("queryString", request.getQueryString());
        PyDictionary pyHeaders = new PyDictionary();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            pyHeaders.put(header, request.getHeader(header));
        }
        pyRequest.put("headers", pyHeaders);
        PyDictionary pyParameters = new PyDictionary();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            PyList pyParameterValues = new PyList();
            for (String paramValue : entry.getValue()) {
                pyParameterValues.pyadd(new PyString(paramValue));
            }
            pyParameters.put(entry.getKey(), pyParameterValues);
        }
        pyRequest.put("parameterMap", pyParameters);
        PyList pyCookies = new PyList();

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                PyDictionary pyCookie = new PyDictionary();
                pyCookie.put("name", cookie.getName());
                pyCookie.put("value", cookie.getValue());
                pyCookie.put("domain", cookie.getDomain());
                pyCookie.put("path", cookie.getPath());
                pyCookie.put("version", cookie.getVersion());
                pyCookie.put("maxAge", cookie.getMaxAge());
                pyCookie.put("secure", cookie.getSecure());
                pyCookie.put("comment", cookie.getComment());
                pyCookies.pyadd(pyCookie);
            }
        }
        pyRequest.put("cookies", pyCookies);
        pyRequest.put("requestBody", new PyString(getRequestBody(request)));
        logger.info(pyRequest.toString());
        PyObject pyResult = mockProcessFunc.__call__(pyRequest);

        logger.info(pyResult.toString());
        return new Gson().getAdapter(PyResult.class).fromJson(pyResult.toString());
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] chars = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(chars)) > 0) {
                    stringBuilder.append(chars, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            logger.error("getRequestBody", e);
            throw e;
        } finally {
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    @Data
    class PyResult {
        String response;
        Integer statusCode;
        String contentType;
    }
}
