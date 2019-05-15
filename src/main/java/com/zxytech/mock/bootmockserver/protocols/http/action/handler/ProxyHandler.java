package com.zxytech.mock.bootmockserver.protocols.http.action.handler;

import com.zxytech.mock.bootmockserver.protocols.http.action.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionHandler;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionType;
import com.zxytech.mock.bootmockserver.protocols.http.action.ProxyActionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@HttpMockActionType("proxy")
public class ProxyHandler implements HttpMockActionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProxyHandler.class);

  RestTemplate restTemplate;

    public ProxyHandler() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public boolean process(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain chain,
      AbstractActionEntity actionEntity)
      throws Exception {
    String target = null;
      if (actionEntity instanceof ProxyActionEntity) {
          target = ((ProxyActionEntity) actionEntity).getTarget();
      logger.info("Forward: {}", target);
    }
    if (StringUtils.isEmpty(target)) {
      throw new Exception("Target must not be null!");
    }

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
    Map<String, String[]> params = request.getParameterMap();
    ResponseEntity<String> responseEntity = null;
    if (httpMethod.matches(HttpMethod.GET.name())) {
      responseEntity = restTemplate.getForEntity(target, String.class, params);
      logger.info(
          "Forward GET {}, get {} {}",
          target,
          responseEntity.getStatusCodeValue(),
          responseEntity.getStatusCode().getReasonPhrase());
    } else if (httpMethod.matches(HttpMethod.POST.name())) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType(request.getContentType()));
      MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
      for (Map.Entry<String, String[]> entry : params.entrySet()) {
        for (String value : entry.getValue()) {
          postData.add(entry.getKey(), value);
        }
      }
      HttpEntity<MultiValueMap<String, String>> postEntity = new HttpEntity<>(postData, headers);
      responseEntity = restTemplate.postForEntity(target, postEntity, String.class);
      logger.info(
          "Forward POST {}, get {} {}",
          target,
          responseEntity.getStatusCodeValue(),
          responseEntity.getStatusCode().getReasonPhrase());
    }

    if (responseEntity != null) {
      MediaType contentType = responseEntity.getHeaders().getContentType();
      response.setContentType(
          contentType != null ? contentType.toString() : MediaType.TEXT_HTML_VALUE);
      response.getWriter().println(responseEntity.getBody());
      return true;
    }

    return false;
  }
}
