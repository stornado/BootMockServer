package com.zxytech.mock.bootmockserver.protocols.http.action.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("代理")
public class ProxyActionEntity extends AbstractActionEntity {
    @ApiModelProperty("目标URL")
    @NotBlank
    @NotNull
    @Size(min = 10, max = 255)
  String target;

  @Override
  public String toString() {
      return "ProxyActionEntity{" + "target='" + target + '\'' + ", name='" + name + '\'' + '}';
  }
}
