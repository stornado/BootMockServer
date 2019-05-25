package com.zxytech.mock.bootmockserver.protocols.http.action.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("延迟响应")
public class DelayedActionEntity extends AbstractActionEntity {
  @ApiModelProperty("延迟时间(ms)")
  @PositiveOrZero
  @Max(10 * 60 * 1000)
  int duration;

  @Override
  public String toString() {
    return "DelayedActionEntity{" + "duration=" + duration + ", name='" + name + '\'' + '}';
  }
}
