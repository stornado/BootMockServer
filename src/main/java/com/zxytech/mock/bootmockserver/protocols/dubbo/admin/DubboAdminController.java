package com.zxytech.mock.bootmockserver.protocols.dubbo.admin;

import com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi.DubboMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi.DubboMockApiRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/__admin/dubbo")
@Api(value = "DubboAdminController", description = "Dubbo Mock 配置管理接口")
public class DubboAdminController {
  @Autowired DubboMockApiRepository apiRepository;

  @GetMapping("/services")
  public List<DubboMockApiEntity> getDubboMockApis() {
    return apiRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
  }

  @GetMapping("/service/{id}")
  public Optional<DubboMockApiEntity> getDubboMockApiById(@PathVariable String id) {
    return apiRepository.findById(id);
  }

  @PostMapping("/service")
  public DubboMockApiEntity addDubboMockApi(@Valid @RequestBody DubboMockApiEntity apiEntity) {
    return apiRepository.save(apiEntity);
  }

  @DeleteMapping("/service/{id}")
  public void deleteDubboMockApi(@PathVariable String id) {
    apiRepository.deleteById(id);
  }

  @PutMapping("/service/{id}")
  public DubboMockApiEntity updateDubboMockApi(
      @PathVariable String id, @Valid @RequestBody DubboMockApiEntity apiEntity) {
    apiEntity.setId(id);
    return apiRepository.save(apiEntity);
  }
}
