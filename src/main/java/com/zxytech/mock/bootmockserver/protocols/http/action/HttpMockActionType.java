package com.zxytech.mock.bootmockserver.protocols.http.action;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HttpMockActionType {
  String value();
}
