package com.zxytech.mock.bootmockserver.protocols.http.admin;

import com.zxytech.mock.bootmockserver.protocols.http.action.domain.ScriptTypeEnum;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiEntity;
import com.zxytech.mock.bootmockserver.protocols.http.mockapi.HttpMockApiRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zxytech.mock.bootmockserver.protocols.http.action.handler.ScriptHandler.SCRIPT_UPLOAD_DIR;

@RestController
@RequestMapping("/__admin/http")
@Api(
    value = "HttpAdminController",
    description = "HTTP Mock 配置管理接口",
    authorizations = {@Authorization("仲夏叶")})
public class HttpAdminController {
    private static final Logger logger = LoggerFactory.getLogger(HttpAdminController.class);

    HttpMockApiRepository apiRepository;
    Resource scriptsDir;

    @Autowired
    public HttpAdminController(HttpMockApiRepository apiRepository) {
        this.apiRepository = apiRepository;
        this.scriptsDir = new DefaultResourceLoader().getResource(SCRIPT_UPLOAD_DIR);
    }

  @GetMapping("/apis")
  public List<HttpMockApiEntity> getHttpMockApis(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
    return apiRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
  }

  @GetMapping("/api/{id}")
  public Optional<HttpMockApiEntity> getHttpMockApiById(@PathVariable String id) {
    return apiRepository.findById(id);
  }

  @PostMapping("/api")
  public HttpMockApiEntity addHttpMockApi(@Valid @RequestBody HttpMockApiEntity httpMockApiEntity) {
    return apiRepository.save(httpMockApiEntity);
  }

  @DeleteMapping("/api/{id}")
  public void deleteHttpMockApi(@PathVariable String id) {
    apiRepository.deleteById(id);
  }

  @PutMapping("/api/{id}")
  public HttpMockApiEntity updateHttpMockApi(
      @PathVariable String id, @Valid @RequestBody HttpMockApiEntity apiEntity) {
    apiEntity.setId(id);
    return apiRepository.save(apiEntity);
  }

    @PostMapping("/script")
    public String uploadScript(@NotNull MultipartFile file) throws IOException {
        logger.info(
            "{},{},{},{}",
            file.getName(),
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize());
        return copyFileToScripts(file).getFileName().toString();
    }

    private Path copyFileToScripts(@NotNull MultipartFile file) throws IOException {
        String scriptType = getScriptType(file).name().toLowerCase();
        String scriptExtension = getFileExtnsion(file.getOriginalFilename());
        Path tempFilePath =
            Paths.get(
                scriptsDir.getFile().getPath(),
                scriptType,
                String.format(
                    "%s_%s%s", UUID.randomUUID(), file.getOriginalFilename(), scriptExtension));
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, tempFilePath);
        }
        return tempFilePath;
    }

    private String getFileExtnsion(@Nullable String name) {
        return name == null ? "" : name.substring(name.lastIndexOf(".")).toLowerCase();
    }

    private ScriptTypeEnum getScriptType(@NotNull MultipartFile file) {
        String contentType = file.getContentType();
        if (null == contentType) {
            contentType = "";
        }
        if (contentType.startsWith("text") && contentType.contains("python")) {
            return ScriptTypeEnum.PYTHON;
        } else if (getFileExtnsion(file.getOriginalFilename())
            .equalsIgnoreCase(ScriptTypeEnum.GROOVY.getExtension())) {
            return ScriptTypeEnum.GROOVY;
        }
        throw new NoSuchMethodError("Only Groovy or Python script is Supported");
    }
}
