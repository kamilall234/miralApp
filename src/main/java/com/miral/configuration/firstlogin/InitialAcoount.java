package com.miral.configuration.firstlogin;

import com.miral.configuration.authentication.BCryptPasswordEncoderService;
import com.miral.user.dao.UserRepository;
import com.miral.user.dao.model.User;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Requires(beans = UserRepository.class)
@Singleton
public class InitialAcoount {

  @Inject
  private UserRepository userRepository;

  @Inject
  private BCryptPasswordEncoderService bCryptPasswordEncoderService;

  @EventListener
  @Async
  public void onStartupEvent(StartupEvent event) {
    var user = userRepository.findByUsername("admin");

    if (user.isEmpty()) {
      var userAdmin = new User("admin", bCryptPasswordEncoderService.encode("password"));
      userRepository.save(userAdmin);
    }
  }
}
