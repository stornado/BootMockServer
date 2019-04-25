package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MediaTypeSerializer extends JsonSerializer<ContentTypeEnum> {

  @Override
  public void serialize(ContentTypeEnum value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.toString());
  }
}
