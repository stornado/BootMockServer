package com.zxytech.mock.bootmockserver.protocols.http.action;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DelayedActionEntity extends AbstractActionEntity {
  int duration;

  @Override
  public String toString() {
    return "DelayedActionEntity{" + "duration=" + duration + ", name='" + name + '\'' + '}';
  }
}
