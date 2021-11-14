package com.miral.user.dao;

import com.miral.user.dao.model.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import javax.validation.constraints.NotNull;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(@NotNull String username);

  boolean existsByUsername(@NotNull String username);
}
