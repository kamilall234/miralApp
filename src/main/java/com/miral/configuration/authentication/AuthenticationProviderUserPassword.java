package com.miral.configuration.authentication;

import static reactor.core.publisher.FluxSink.OverflowStrategy.ERROR;

import com.miral.user.UserDto;
import com.miral.user.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  @Inject
  private UserService userService;

  @Override
  public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
    return Flux.create(emiter -> {
      var userFromRequest = new UserDto(authenticationRequest.getIdentity().toString(), authenticationRequest.getSecret().toString());
      if (userService.validatePassword(userFromRequest)) {
        emiter.next(AuthenticationResponse.success(authenticationRequest.getIdentity().toString()));
        emiter.complete();
      } else {
        emiter.error(AuthenticationResponse.exception("Wrong credentials"));
      }
    }, ERROR);
  }
}
