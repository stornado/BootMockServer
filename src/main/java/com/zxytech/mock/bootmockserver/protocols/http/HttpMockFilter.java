package com.zxytech.mock.bootmockserver.protocols.http;

import com.zxytech.mock.bootmockserver.protocols.http.action.HandlerContext;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionHandler;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class HttpMockFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpMockFilter.class);

  HttpMockApiRepository apiRepository;
  HandlerContext handlerContext;

  @Autowired
  public HttpMockFilter(HttpMockApiRepository apiRepository, HandlerContext handlerContext) {
    this.apiRepository = apiRepository;
    this.handlerContext = handlerContext;
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setHeader("X-Mock-Server", "Boot Mock Server");

    Optional<HttpMockApiEntity> apiEntityOptional =
        apiRepository.findFirstByPathAndMethod(request.getRequestURI(), request.getMethod());

    if (apiEntityOptional.isPresent()) {
      HttpMockApiEntity apiEntity = apiEntityOptional.get();

      logger.info("Request: {} {}", request.getMethod(), request.getPathInfo());
      logger.info(apiEntity.toString());

      try {
        boolean terminal;
        for (AbstractActionEntity actionEntity : apiEntity.getActions()) {
          HttpMockActionHandler actionHandler = handlerContext.getInstance(actionEntity.getName());
          terminal = actionHandler.process(request, response, chain, actionEntity);
          if (terminal) {
            return;
          }
        }
      } catch (Exception e) {
        logger.error(apiEntity.toString(), e);
      }

      response.setContentType(apiEntity.getContentType().toString());
      response.getWriter().println(apiEntity.getResponse());
      return;
    }

    chain.doFilter(request, response);
  }
}
