package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MediaTypeDeserializer extends JsonDeserializer<ContentTypeEnum> {

  @Override
  public ContentTypeEnum deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    return ContentTypeEnum.valueOf(
        MediaType.parseMediaType(p.getValueAsString()).getSubtype().toUpperCase());
  }
}
