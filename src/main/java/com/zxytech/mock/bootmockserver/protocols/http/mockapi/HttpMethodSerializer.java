package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.http.HttpMethod;

import java.io.IOException;

public class HttpMethodSerializer extends JsonSerializer<HttpMethod> {
    @Override
    public void serialize(HttpMethod method, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(method.name());
    }
}
