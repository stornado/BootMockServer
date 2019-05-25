package com.zxytech.mock.bootmockserver.protocols.http.action.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = DelayedActionEntity.class, name = "delay"),
    @JsonSubTypes.Type(value = ProxyActionEntity.class, name = "proxy"),
    @JsonSubTypes.Type(value = ScriptActionEntity.class, name = "script")
})
public abstract class AbstractActionEntity {
  @ApiModelProperty("动作名称")
  @NotNull
  protected String name;

    public String toJson() {
        return new Gson().toJson(this);
    }
}
