package com.zxytech.mock.bootmockserver.protocols.dubbo.mockapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface DubboMockApiRepository extends MongoRepository<DubboMockApiEntity, String> {
  Optional<DubboMockApiEntity> findFirstBySideAndServiceAndVersion(
      @NotNull DubboRoleEnum side, @NotNull String service, @NotNull String version);

  List<DubboMockApiEntity> findBySide(@NotNull DubboRoleEnum side);
}
