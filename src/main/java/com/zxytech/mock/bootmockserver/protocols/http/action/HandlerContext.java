package com.zxytech.mock.bootmockserver.protocols.http.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class HandlerContext {
  private static final Logger logger = LoggerFactory.getLogger(HandlerContext.class);
  private final Map<String, HttpMockActionHandler> handlerMap;

  public HandlerContext(Map<String, HttpMockActionHandler> handlerMap) {
    this.handlerMap = handlerMap;
  }

  public HttpMockActionHandler getInstance(String actionType) {
    logger.info("input action name: ", actionType);
    HttpMockActionHandler handler = handlerMap.get(actionType);
    if (handler != null) {
      return handler;
    }

    throw new IllegalArgumentException(
        String.format(
            "Invalid action: %s, there are only %s allowed.",
            actionType, Arrays.toString(handlerMap.keySet().toArray())));
  }
}
