package com.zxytech.mock.bootmockserver.protocols.http.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("延迟响应")
public class DelayedActionEntity extends AbstractActionEntity {
  @ApiModelProperty("延迟时间(ms)")
  int duration;

  @Override
  public String toString() {
    return "DelayedActionEntity{" + "duration=" + duration + ", name='" + name + '\'' + '}';
  }
}
