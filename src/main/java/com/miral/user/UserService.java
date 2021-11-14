package com.miral.user;


import com.miral.configuration.authentication.BCryptPasswordEncoderService;
import com.miral.user.dao.UserRepository;
import com.miral.user.dao.model.User;
import com.miral.user.exception.UserExistsException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class UserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
  @Inject
  private UserRepository userRepository;

  @Inject
  private BCryptPasswordEncoderService bCryptPasswordEncoderService;

  public UserService(UserRepository userRepository,
                     BCryptPasswordEncoderService bCryptPasswordEncoderService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoderService = bCryptPasswordEncoderService;
  }

  public void createUser(@Valid UserDto userDto) {
    if (checkUserExists(userDto.username())) {
      var newUser = new User(userDto.username(), bCryptPasswordEncoderService.encode(userDto.password()));
      userRepository.save(newUser);
      LOGGER.debug("User created in database with name: {}", newUser.getUsername());
    } else {
      throw new UserExistsException(String.format("User with provided name exists"));
    }
  }

  public UserDto getUserByName(String username) {
    var user = userRepository.findByUsername(username).orElseThrow();

    return new UserDto(user.getUsername(), user.getPassword());
  }

  public boolean validatePassword(UserDto userDto) {
    var user = getUserByName(userDto.username());

    return bCryptPasswordEncoderService.matches(userDto.password(), user.password());
  }

  private boolean checkUserExists(String username) {
    return userRepository.existsByUsername(username);
  }
}
