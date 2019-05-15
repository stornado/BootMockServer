package com.zxytech.mock.bootmockserver.protocols.http.admin;

import javax.validation.constraints.NotNull;
import java.util.List;

public class HttpMockApiForm {
    @NotNull String path;
    @NotNull String method;
    @NotNull String contentType;
    List<String> actions;
    String response;
    @NotNull Boolean active;
    String description;
}
