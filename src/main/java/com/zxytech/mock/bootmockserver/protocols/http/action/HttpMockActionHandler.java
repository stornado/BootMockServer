package com.zxytech.mock.bootmockserver.protocols.http.action;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface HttpMockActionHandler {
  boolean process(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain,
      AbstractActionEntity action)
      throws Exception;
}
