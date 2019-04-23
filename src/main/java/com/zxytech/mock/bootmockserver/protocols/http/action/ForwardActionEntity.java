package com.zxytech.mock.bootmockserver.protocols.http.action;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForwardActionEntity extends AbstractActionEntity {
  String target;

  @Override
  public String toString() {
    return "ForwardActionEntity{" + "target='" + target + '\'' + ", name='" + name + '\'' + '}';
  }
}
