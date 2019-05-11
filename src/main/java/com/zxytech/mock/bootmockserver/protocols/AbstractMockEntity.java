package com.zxytech.mock.bootmockserver.protocols;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
public abstract class AbstractMockEntity {
  @Id String id;

  @NotNull
  @ApiModelProperty("Mock 有效状态(默认true)")
  boolean active;

  @NotNull
  @ApiModelProperty("Mock 接口备注")
  String description;

  Long createTime;
  long updateTime;

  protected AbstractMockEntity() {
    active = true;
    createTime = System.currentTimeMillis();
    updateTime = createTime;
  }
}
