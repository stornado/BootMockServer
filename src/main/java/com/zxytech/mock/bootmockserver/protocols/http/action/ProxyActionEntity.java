package com.zxytech.mock.bootmockserver.protocols.http.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("代理")
public class ProxyActionEntity extends AbstractActionEntity {
    @ApiModelProperty("目标URL")
  String target;

  @Override
  public String toString() {
      return "ProxyActionEntity{" + "target='" + target + '\'' + ", name='" + name + '\'' + '}';
  }
}
