package com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi;

import com.zxytech.mock.bootmockserver.protocols.AbstractMockEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document("dubbo_mock_apis")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel("Dubbo Mock 接口实体")
public class DubboMockApiEntity extends AbstractMockEntity {
  @ApiModelProperty("注册中心地址")
  String register;

  @NotBlank
  @ApiModelProperty("接口名")
  String service;

  @NotBlank
  @ApiModelProperty("接口版本")
  String version;

  @NotBlank
  @ApiModelProperty("接口方法")
  String method;
}
