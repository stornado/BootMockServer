package com.zxytech.mock.bootmockserver.admin;

import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/__admin")
public class AdminController {

  @Autowired HttpMockApiRepository apiRepository;

  @GetMapping("/apis")
  public List<HttpMockApiEntity> getHttpMockApis(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
    return apiRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
  }

  @GetMapping("/api/{id}")
  public Optional<HttpMockApiEntity> getHttpMockApiById(@PathVariable String id) {
    return apiRepository.findById(id);
  }

  @PostMapping("/api")
  public HttpMockApiEntity addHttpMockApi(@RequestBody HttpMockApiEntity httpMockApiEntity) {
    return apiRepository.save(httpMockApiEntity);
  }

  @DeleteMapping("/api/{id}")
  public void deleteHttpMockApi(@PathVariable String id) {
    apiRepository.deleteById(id);
  }
}
