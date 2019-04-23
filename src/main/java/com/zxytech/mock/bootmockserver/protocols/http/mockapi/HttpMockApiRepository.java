package com.zxytech.mock.bootmockserver.protocols.http.mockapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface HttpMockApiRepository extends MongoRepository<HttpMockApiEntity, String> {

  Optional<HttpMockApiEntity> findFirstByPathAndMethod(
      @NotNull String path, @NotNull String method);
}
