package com.zxytech.mock.bootmockserver.protocols.http.action.domain;

public enum ScriptTypeEnum {
  PYTHON(".py"),
  GROOVY(".groovy");

  String extension;

  ScriptTypeEnum(String extension) {
    this.extension = extension;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }
}
