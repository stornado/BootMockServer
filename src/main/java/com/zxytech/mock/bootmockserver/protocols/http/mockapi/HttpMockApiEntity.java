package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zxytech.mock.bootmockserver.protocols.AbstractMockEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.AbstractActionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document("http_mock_apis")
@Data
@NoArgsConstructor
public class HttpMockApiEntity extends AbstractMockEntity {
  @Id String id;
  @NotBlank @Indexed String path;
  List<AbstractActionEntity> actions;

  @NotNull
  @JsonDeserialize(using = HttpMethodDeserializer.class)
  @JsonSerialize(using = HttpMethodSerializer.class)
  HttpMethod method;

  @NotNull
  @JsonDeserialize(using = MediaTypeDeserializer.class)
  @JsonSerialize(using = MediaTypeSerializer.class)
  ContentTypeEnum contentType;

  @NotEmpty String response;

  @Override
  public String toString() {
    return "HttpMockApiEntity{"
        + "path='"
        + path
        + '\''
        + ", actions="
        + actions
        + ", method="
        + method
        + ", contentType="
        + contentType
        + ", response='"
        + response
        + '\''
        + '}';
  }
}
