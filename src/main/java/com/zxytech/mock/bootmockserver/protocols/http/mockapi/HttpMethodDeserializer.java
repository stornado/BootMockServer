package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.http.HttpMethod;

import java.io.IOException;

public class HttpMethodDeserializer extends JsonDeserializer<HttpMethod> {
  @Override
  public HttpMethod deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return HttpMethod.resolve(p.getValueAsString().toUpperCase());
  }
}
