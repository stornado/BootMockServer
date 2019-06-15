package com.zxytech.mock.bootmockserver.protocols.dubbo;

import com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi.DubboMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi.DubboMockApiRepository;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DubboMockFilter implements Filter {
  public static final Logger logger = LoggerFactory.getLogger(DubboMockFilter.class);
  private DubboMockApiRepository apiRepository;

  @Autowired
  public DubboMockFilter(DubboMockApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    logger.info(invoker.toString());
    logger.info(invocation.toString());
    Optional<DubboMockApiEntity> apiEntityOptional =
      apiRepository.findFirstServiceAndMethodAndVersionAndActiveIsTrueOrderByUpdateTime(
        invoker.getUrl().getPath(), invocation.getMethodName(), "1.0.0");
    if (apiEntityOptional.isPresent()) {
      DubboMockApiEntity apiEntity = apiEntityOptional.get();
      apiEntity.getResponse();
    }
    return null;
  }
}
