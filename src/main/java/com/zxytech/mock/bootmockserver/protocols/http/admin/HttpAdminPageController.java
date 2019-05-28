package com.zxytech.mock.bootmockserver.protocols.http.admin;

import com.zxytech.mock.bootmockserver.config.HttpHandlerProperties;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.ScriptTypeEnum;
import com.zxytech.mock.bootmockserver.protocols.http.action.handler.ScriptHandler;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/__admin/http")
@Api(value = "HttpAdminPageController", description = "HTTP Mock 管理页面", hidden = true)
public class HttpAdminPageController {
    private static final Logger logger = LoggerFactory.getLogger(HttpAdminPageController.class);

    private HttpMockApiRepository apiRepository;
    private Resource scriptsDir;
    private ScriptHandler scriptHandler;

    @Autowired
    public HttpAdminPageController(
        HttpMockApiRepository apiRepository,
        HttpHandlerProperties handlerProperties,
        ScriptHandler scriptHandler) {
        this.apiRepository = apiRepository;
        this.scriptsDir = handlerProperties.getScriptUploadPath();
        this.scriptHandler = scriptHandler;
    }

    @GetMapping(value = "/apis/page", produces = MediaType.TEXT_HTML_VALUE)
    public String httpMockApiListPage(Model model) {
        List<HttpMockApiEntity> apiEntityList =
            apiRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
        model.addAttribute("httpMockApiList", apiEntityList);
        return "pages/admin/http/mock_api_list_page.html";
    }

    @GetMapping(value = "/api/{id}/page", produces = MediaType.TEXT_HTML_VALUE)
    public String httpMockApiPage(@PathVariable String id, Model model) {
        model.addAttribute("httpMockApi", apiRepository.findById(id).orElse(new HttpMockApiEntity()));
        return "pages/admin/http/mock_api_page.html";
    }

    @GetMapping("/api/{id}/{enableOrDisable}")
    public String enableOrDisableHttpMockApi(
        @PathVariable String id, @PathVariable boolean enableOrDisable) {
        Optional<HttpMockApiEntity> apiEntityOptional = apiRepository.findById(id);

        apiEntityOptional.ifPresent(
            api -> {
                api.setActive(enableOrDisable);
                api.setUpdateTime(System.currentTimeMillis());
                apiRepository.save(api);
            });
        return "redirect:/__admin/http/apis/page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"saveApi"})
    public String updateOrCreateHttpMockApi(
        @PathVariable @NotNull String id, @Valid HttpMockApiEntity apiEntity, BindingResult result) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
        }
        apiEntity.setId(apiRepository.findById(id).orElse(new HttpMockApiEntity()).getId());
        apiEntity.setUpdateTime(System.currentTimeMillis());

        apiRepository.save(apiEntity);
        return "redirect:/__admin/http/apis/page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"addAction"})
    public String addAction(
        @PathVariable @NotNull String id, HttpMockApiEntity apiEntity, Model model) {
        List<AbstractActionEntity> actionEntities = apiEntity.getActions();
        if (null == actionEntities) {
            actionEntities = new ArrayList<>(1);
        }
        actionEntities.add(null);
        apiEntity.setActions(actionEntities);
        model.addAttribute("httpMockApi", apiEntity);
        return "pages/admin/http/mock_api_page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"removeAction"})
    public String removeAction(
        @PathVariable @NotNull String id,
        @RequestParam int removeAction,
        HttpMockApiEntity apiEntity) {
        apiEntity.getActions().remove(removeAction);
        return "pages/admin/http/mock_api_page";
    }

    @PostMapping("/script")
    public @ResponseBody
    String uploadScript(@NotNull MultipartFile file) throws IOException {
        logger.info(
            "{},{},{},{}",
            file.getName(),
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize());
        return copyFileToScripts(file).getFileName().toString();
    }

    @PostMapping("/script/python/requirements")
    public @ResponseBody
    String installRequirements(@NotNull MultipartFile file) throws IOException {
        assert file.getContentType() != null
            && MediaType.valueOf(file.getContentType()).isCompatibleWith(MediaType.TEXT_PLAIN);
        Path tempFilePath =
            Paths.get(
                scriptsDir.getFile().getPath(),
                "python",
                String.format("requirements_%s.txt", UUID.randomUUID()));
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, tempFilePath);
        }
        logger.info("{}\n{}", tempFilePath.getFileName(), new String(Files.readAllBytes(tempFilePath)));
        return scriptHandler.installRequirements(tempFilePath.toAbsolutePath().toString());
    }

    private Path copyFileToScripts(@NotNull MultipartFile file) throws IOException {
        String scriptType = getScriptType(file).name().toLowerCase();
        String scriptExtension = getFileExtnsion(file.getOriginalFilename());
        Path tempFilePath =
            Paths.get(
                scriptsDir.getFile().getPath(),
                scriptType,
                String.format(
                    "%s_%s%s", UUID.randomUUID(), file.getOriginalFilename(), scriptExtension));
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, tempFilePath);
        }
        return tempFilePath;
    }

    private String getFileExtnsion(@Nullable String name) {
        return name == null ? "" : name.substring(name.lastIndexOf(".")).toLowerCase();
    }

    private ScriptTypeEnum getScriptType(@NotNull MultipartFile file) {
        String contentType = file.getContentType();
        if (null == contentType) {
            contentType = "";
        }
        if (contentType.startsWith("text") && contentType.contains("python")) {
            return ScriptTypeEnum.PYTHON;
        } else if (getFileExtnsion(file.getOriginalFilename())
            .equalsIgnoreCase(ScriptTypeEnum.GROOVY.getExtension())) {
            return ScriptTypeEnum.GROOVY;
        }
        throw new NoSuchMethodError("Only Groovy or Python script is Supported");
    }
}
