package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = DelayedActionEntity.class, name = "delay"),
  @JsonSubTypes.Type(value = ForwardActionEntity.class, name = "forward")
})
public abstract class AbstractActionEntity {
  protected String name;
  protected String description;
}
