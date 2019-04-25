package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import org.springframework.http.MediaType;

public enum ContentTypeEnum {
  TEXT(MediaType.TEXT_PLAIN),
  JSON(MediaType.APPLICATION_JSON_UTF8),
  HTML(MediaType.TEXT_HTML),
  XML(MediaType.TEXT_XML);

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
