package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import org.springframework.http.MediaType;

public enum ContentTypeEnum {
  TEXT(MediaType.TEXT_PLAIN),
  JSON(MediaType.APPLICATION_JSON),
  HTML(MediaType.TEXT_HTML),
  XML(MediaType.APPLICATION_XML);

  MediaType mediaType;

  ContentTypeEnum(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  @Override
  public String toString() {
    return mediaType.toString();
  }
}
