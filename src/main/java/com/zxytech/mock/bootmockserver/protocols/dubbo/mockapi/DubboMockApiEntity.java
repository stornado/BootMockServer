package com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi;

import com.zxytech.mock.bootmockserver.protocols.AbstractMockEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("dubbo_mock_apis")
@Data
@NoArgsConstructor
@ApiModel("Dubbo Mock 接口实体")
public class DubboMockApiEntity extends AbstractMockEntity {
  String register;
  String service;
  String version;
  DubboRoleEnum side;
  String method;
  String response;
}
