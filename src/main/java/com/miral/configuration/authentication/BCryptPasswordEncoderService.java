package com.miral.configuration.authentication;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
public class BCryptPasswordEncoderService implements PasswordEncoder {
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public String encode(CharSequence rawPassword) {
    return encoder.encode(rawPassword);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}
