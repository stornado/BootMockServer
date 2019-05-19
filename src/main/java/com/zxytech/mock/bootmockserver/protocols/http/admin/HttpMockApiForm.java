package com.zxytech.mock.bootmockserver.protocols.http.admin;

import com.google.gson.Gson;
import com.zxytech.mock.bootmockserver.protocols.http.action.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.ContentTypeEnum;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class HttpMockApiForm {
    private static final Logger logger = LoggerFactory.getLogger(HttpMockApiForm.class);

    String id;
    @NotNull String path;
    @NotNull String method;
    @NotNull String contentType;
    List<String> actions;
    String response;
    @NotNull Boolean active;
    String description;

    public HttpMockApiForm(HttpMockApiEntity apiEntity) {
        if (null == apiEntity) {
            return;
        }
        id = apiEntity.getId();
        path = apiEntity.getPath();
        method = apiEntity.getMethod().name();
        contentType = apiEntity.getContentType().name();
        if (null != apiEntity.getActions()) {
            actions = new ArrayList<>(apiEntity.getActions().size());
            for (AbstractActionEntity actionEntity : apiEntity.getActions()) {
                actions.add(actionEntity.toJson());
            }
        } else {
            actions = null;
        }
        response = apiEntity.getResponse();
        active = apiEntity.isActive();
        description = apiEntity.getDescription();
    }

    public HttpMockApiEntity toHttpMockApiEntity() {
        HttpMockApiEntity apiEntity = new HttpMockApiEntity();
        apiEntity.setId(id);
        apiEntity.setPath(path);
        apiEntity.setMethod(HttpMethod.valueOf(method));
        apiEntity.setContentType(ContentTypeEnum.valueOf(contentType));
        List<AbstractActionEntity> actionEntities = new ArrayList<>(actions.size());
        for (String action : actions) {
            try {
                AbstractActionEntity actionEntity =
                    new Gson().getAdapter(AbstractActionEntity.class).fromJson(action);

                actionEntities.add(actionEntity);
            } catch (IOException e) {
                logger.error(action, e);
            }
        }
        apiEntity.setActions(actionEntities);
        apiEntity.setResponse(response);
        apiEntity.setActive(active);
        apiEntity.setDescription(description);
        return apiEntity;
    }
}
