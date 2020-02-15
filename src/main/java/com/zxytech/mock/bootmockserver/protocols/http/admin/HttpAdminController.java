package com.zxytech.mock.bootmockserver.protocols.http.admin;

import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/__admin/http")
@Api(
    value = "HttpAdminController",
    description = "HTTP Mock 配置管理接口",
    authorizations = {@Authorization("仲夏叶")})
public class HttpAdminController {
  private static final Logger logger = LoggerFactory.getLogger(HttpAdminController.class);

  private HttpMockApiRepository apiRepository;

  @Autowired
  public HttpAdminController(HttpMockApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  @GetMapping("/apis")
  public List<HttpMockApiEntity> getHttpMockApis(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
    return apiRepository.findAll(Sort.by(Sort.Direction.DESC, "updateTime"));
  }

  @GetMapping("/api/{id}")
  public Optional<HttpMockApiEntity> getHttpMockApiById(@PathVariable String id) {
    return apiRepository.findById(id);
  }

  @PostMapping("/api")
  public HttpMockApiEntity addHttpMockApi(@Valid @RequestBody HttpMockApiEntity httpMockApiEntity) {
    return apiRepository.save(httpMockApiEntity);
  }

  @DeleteMapping("/api/{id}")
  public void deleteHttpMockApi(@PathVariable String id) {
    apiRepository.deleteById(id);
  }

  @PutMapping("/api/{id}")
  public HttpMockApiEntity updateHttpMockApi(
      @PathVariable String id, @Valid @RequestBody HttpMockApiEntity apiEntity) {
    apiEntity.setId(id);
    return apiRepository.save(apiEntity);
  }
}
