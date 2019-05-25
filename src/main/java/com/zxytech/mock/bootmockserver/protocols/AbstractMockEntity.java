package com.zxytech.mock.bootmockserver.protocols;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public abstract class AbstractMockEntity {
  @NotNull
  @ApiModelProperty("Mock 有效状态(默认true)")
  protected boolean active;
  @NotNull
  @ApiModelProperty("接口备注")
  protected String description;
    @NotEmpty
    @ApiModelProperty("Mock 默认响应内容")
    protected String response;
    protected Long createTime;
    protected Long updateTime;
    @Id
    String id;

  protected AbstractMockEntity() {
    active = true;
    createTime = System.currentTimeMillis();
    updateTime = createTime;
  }
}
