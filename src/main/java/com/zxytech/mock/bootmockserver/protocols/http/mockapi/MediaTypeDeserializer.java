package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MediaTypeDeserializer extends JsonDeserializer<MediaType> {
  @Override
  public MediaType deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    return MediaType.parseMediaType(p.getValueAsString());
  }
}
