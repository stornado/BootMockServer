package com.zxytech.mock.bootmockserver.protocols.http.action.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("执行脚本")
public class ScriptActionEntity extends AbstractActionEntity {
    @ApiModelProperty("脚本路径")
    @NotNull
    @NotBlank
    @Size(min = 4, max = 50)
    String scriptPath;

    @NotNull ScriptTypeEnum scriptType;

    @Override
    public String toString() {
        return "ScriptActionEntity{"
            + "scriptFilePath='"
            + scriptPath
            + '\''
            + ", scriptType="
            + scriptType
            + ", name='"
            + name
            + '\''
            + '}';
    }
}
