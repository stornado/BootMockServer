package com.zxytech.mock.bootmockserver.protocols.mq.mockapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("RocketMQ Mock 实体")
public class RocketMqApiEntity {
  @ApiModelProperty("注册中心地址")
  String registerAddr;

  String group;
  String topic;
  String tag;
}
