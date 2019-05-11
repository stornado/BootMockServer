package com.zxytech.mock.bootmockserver.protocols.http.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("转发")
public class ForwardActionEntity extends AbstractActionEntity {
  @ApiModelProperty("转发目标地址")
  String target;

  @Override
  public String toString() {
    return "ForwardActionEntity{" + "target='" + target + '\'' + ", name='" + name + '\'' + '}';
  }
}
