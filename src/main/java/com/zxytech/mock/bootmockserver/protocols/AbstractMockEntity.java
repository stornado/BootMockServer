package com.zxytech.mock.bootmockserver.protocols;

import lombok.Data;

@Data
public abstract class AbstractMockEntity {
  Long createTime;
  long updateTime;

  protected AbstractMockEntity() {
    createTime = System.currentTimeMillis();
    updateTime = createTime;
  }
}
