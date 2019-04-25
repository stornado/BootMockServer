package com.zxytech.mock.bootmockserver.protocols.http.action.handler;

import com.zxytech.mock.bootmockserver.protocols.http.action.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.DelayedActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionHandler;
import com.zxytech.mock.bootmockserver.protocols.http.action.HttpMockActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@HttpMockActionType("delay")
public class DelayHandler implements HttpMockActionHandler {
  private static final Logger logger = LoggerFactory.getLogger(DelayHandler.class);
  private static final int DEFAULT_DELAY_DURATION = 1000;

  @Override
  public boolean process(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain,
      AbstractActionEntity actionEntity)
      throws Exception {
    int duration = DEFAULT_DELAY_DURATION;

    if (actionEntity instanceof DelayedActionEntity) {
      duration = ((DelayedActionEntity) actionEntity).getDuration();
    }

    Thread.sleep(duration);
    logger.info("Delayed: {}ms", duration);
    return false;
  }
}
