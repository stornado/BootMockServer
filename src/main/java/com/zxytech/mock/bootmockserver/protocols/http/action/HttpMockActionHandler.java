package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.zxytech.mock.bootmockserver.protocols.http.action.domain.AbstractActionEntity;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpMockActionHandler {
  boolean process(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      AbstractActionEntity action)
      throws Exception;
}
