package com.zxytech.mock.bootmockserver.protocols.dubbo;

import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

public class DubboMockService implements GenericService {
  @Override
  public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
    return null;
  }
}
