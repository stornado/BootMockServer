package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.http.MediaType;

import java.io.IOException;

public class MediaTypeSerializer extends JsonSerializer<MediaType> {
  @Override
  public void serialize(MediaType mediaType, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(mediaType.toString());
  }
}
