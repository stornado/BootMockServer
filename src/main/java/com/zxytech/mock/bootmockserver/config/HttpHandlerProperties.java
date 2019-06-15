package com.zxytech.mock.bootmockserver.config;

import lombok.Data;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.python.util.PythonInterpreter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;

@Component
@ConfigurationProperties(prefix = "mock.http.handler")
@Data
public class HttpHandlerProperties {
  private Resource scriptUploadPath;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ScriptEngine groovyScriptEngine() {
    return new GroovyScriptEngineImpl();
  }

  @Bean
  public PythonInterpreter pythonInterpreter() {
    return new PythonInterpreter();
  }
}
