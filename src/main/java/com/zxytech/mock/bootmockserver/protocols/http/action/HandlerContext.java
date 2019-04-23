package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HandlerContext {
  private static final Logger logger = LoggerFactory.getLogger(HandlerContext.class);
  private static final String HANDLER_PACKAGE =
      HandlerContext.class.getPackage().getName() + ".handler.";

  public HttpMockActionHandler getInstance(String actionType) throws Exception {
    String handler =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, actionType + "_handler");
    logger.info(handler);

    Class<?> actionHandlerClass = Class.forName(HANDLER_PACKAGE + handler);
    if (actionHandlerClass != null) {
      logger.info("find action handler: {}", actionHandlerClass);
      return (HttpMockActionHandler) actionHandlerClass.newInstance();
    }

    throw new ClassNotFoundException("input action name invalid");
  }
}
