package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zxytech.mock.bootmockserver.protocols.AbstractMockEntity;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.AbstractActionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document("http_mock_apis")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("HTTP Mock 接口实体")
public class HttpMockApiEntity extends AbstractMockEntity {

  @NotBlank
  @Indexed
  @ApiModelProperty("HTTP Mock URL 路径")
  String path;

  @ApiModelProperty("动作")
  List<AbstractActionEntity> actions;

  @NotNull
  @JsonDeserialize(using = HttpMethodDeserializer.class)
  @JsonSerialize(using = HttpMethodSerializer.class)
  HttpMethod method;

  @NotNull
  @JsonDeserialize(using = MediaTypeDeserializer.class)
  @JsonSerialize(using = MediaTypeSerializer.class)
  @ApiModelProperty("Mock 响应 Content-Type")
  ContentTypeEnum contentType;

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
