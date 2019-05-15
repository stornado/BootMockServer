package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = DelayedActionEntity.class, name = "delay"),
    @JsonSubTypes.Type(value = ProxyActionEntity.class, name = "proxy")
})
public abstract class AbstractActionEntity {
  @ApiModelProperty("动作名称")
  protected String name;

    public String toJson() {
        return new Gson().toJson(this);
    }
}
