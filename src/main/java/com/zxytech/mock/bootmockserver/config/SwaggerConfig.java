package com.zxytech.mock.bootmockserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Value("${swagger.api.title}")
  private static String SWAGGER_TITLE;

  @Value("${swagger.api.description}")
  private static String SWAGGER_DESCRIPTION;

  @Value("${swagger.api.version}")
  private static String SWAGGER_VERSION;

  @Value("${swagger.api.termsOfServiceUrl}")
  private static String SWAGGER_TERMS_OF_SERVICE_URL;

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zxytech.mock.bootmockserver"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(SWAGGER_TITLE)
        .description(SWAGGER_DESCRIPTION)
        .version(SWAGGER_VERSION)
        .termsOfServiceUrl(SWAGGER_TERMS_OF_SERVICE_URL)
        .build();
  }
}
