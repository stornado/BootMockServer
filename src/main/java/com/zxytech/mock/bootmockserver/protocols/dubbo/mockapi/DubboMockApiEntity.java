package com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi;

import com.zxytech.mock.bootmockserver.protocols.AbstractMockEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document("dubbo_mock_apis")
@Data
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

    @NotNull
    @ApiModelProperty("角色")
    DubboRoleEnum side;

    @NotBlank
    @ApiModelProperty("接口方法")
    String method;
}
