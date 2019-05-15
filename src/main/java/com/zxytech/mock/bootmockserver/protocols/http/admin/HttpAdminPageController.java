package com.zxytech.mock.bootmockserver.protocols.http.admin;

import com.zxytech.mock.bootmockserver.protocols.http.action.AbstractActionEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/__admin/http")
@Api(value = "HttpAdminPageController", description = "HTTP Mock 管理页面", hidden = true)
public class HttpAdminPageController {
    private static final Logger logger = LoggerFactory.getLogger(HttpAdminPageController.class);

    private HttpMockApiRepository apiRepository;

    @Autowired
    public HttpAdminPageController(HttpMockApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping(value = "/apis/page", produces = MediaType.TEXT_HTML_VALUE)
    public String httpMockApiListPage(Model model) {
        List<HttpMockApiEntity> apiEntityList =
            apiRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
        model.addAttribute("httpMockApiList", apiEntityList.subList(0, 10));
        return "pages/admin/http/mock_api_list_page.html";
    }

    @GetMapping(value = "/api/{id}/page", produces = MediaType.TEXT_HTML_VALUE)
    public String httpMockApiPage(@PathVariable String id, Model model) {
        model.addAttribute("httpMockApi", apiRepository.findById(id).orElse(new HttpMockApiEntity()));
        return "pages/admin/http/mock_api_page.html";
    }

    @GetMapping("/api/{id}/{enableOrDisable}")
    public String enableOrDisableHttpMockApi(
        @PathVariable String id, @PathVariable boolean enableOrDisable) {
        Optional<HttpMockApiEntity> apiEntityOptional = apiRepository.findById(id);

        apiEntityOptional.ifPresent(
            api -> {
                api.setActive(enableOrDisable);
                api.setUpdateTime(System.currentTimeMillis());
                apiRepository.save(api);
            });
        return "redirect:/__admin/http/apis/page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"saveApi"})
    public String updateOrCreateHttpMockApi(
        @PathVariable @NotNull String id, @Valid HttpMockApiEntity apiEntity, BindingResult result) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
        }
        apiEntity.setId(apiRepository.findById(id).orElse(new HttpMockApiEntity()).getId());
        apiEntity.setUpdateTime(System.currentTimeMillis());

        apiRepository.save(apiEntity);
        return "redirect:/__admin/http/apis/page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"addAction"})
    public String addAction(
        @PathVariable @NotNull String id, HttpMockApiEntity apiEntity, Model model) {
        List<AbstractActionEntity> actionEntities = apiEntity.getActions();
        if (null == actionEntities) {
            actionEntities = new ArrayList<>(1);
        }
        actionEntities.add(null);
        apiEntity.setActions(actionEntities);
        model.addAttribute("httpMockApi", apiEntity);
        return "pages/admin/http/mock_api_page";
    }

    @PostMapping(
        value = "/api/{id}/page",
        params = {"removeAction"})
    public String removeAction(
        @PathVariable @NotNull String id,
        @RequestParam int removeAction,
        HttpMockApiEntity apiEntity) {
        apiEntity.getActions().remove(removeAction);
        return "pages/admin/http/mock_api_page";
    }
}
