package com.miral.configuration.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

import com.miral.products.controller.dto.ProductDto;
import com.miral.products.services.Eproduktyservice;
import com.miral.user.UserDto;
import com.miral.user.UserService;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


@MicronautTest
class AuthenticationProviderUserPasswordTest {

  @Inject
  private Eproduktyservice eproduktyservice;

  @Inject
  private BCryptPasswordEncoderService bCryptPasswordEncoderService;

  @Inject
  private UserService userService;


  @Inject
  @Client("/")
  HttpClient httpClient;

  @BeforeEach
  void setUp() {
    var product = new ProductDto("123", "name", 123f, "unit", "brand");
    Mockito.when(eproduktyservice.getProductDtoByGtInNumber(Mockito.any(String.class))).thenReturn(product);
  }


  @Test
  void whenWrongUserNameAndPasswordThenUnathorized() {
    var e = assertThrows(HttpClientResponseException.class,
        () -> httpClient.toBlocking().exchange(HttpRequest.GET("/login").accept(MediaType.TEXT_PLAIN_TYPE)));

    assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
  }

  @Test
  void whenCorrectCredentialThenAuthorized() throws ParseException {
    var credentials = new UsernamePasswordCredentials("admin", "password");
    var request = HttpRequest.POST("/login", credentials);
    Mockito.when(userService.validatePassword(any(UserDto.class))).thenReturn(true);
    var response = httpClient.toBlocking().exchange(request, BearerAccessRefreshToken.class);
    assertEquals(HttpStatus.OK, response.getStatus());

    var responseTokenBody = response.body();
    assertEquals(responseTokenBody.getTokenType(), "Bearer");
    assertTrue(JWTParser.parse(responseTokenBody.getAccessToken()) instanceof SignedJWT);

    var accessToken = responseTokenBody.getAccessToken();
    var requestWithAccess = HttpRequest.GET("/product?gtinNumber=123")
        .bearerAuth(accessToken)
        .contentType(MediaType.APPLICATION_JSON_TYPE);
    var athorizedResponse = httpClient.toBlocking().exchange(requestWithAccess, String.class);
    assertNotEquals(athorizedResponse.getStatus(), HttpStatus.UNAUTHORIZED);

  }

  @MockBean(Eproduktyservice.class)
  Eproduktyservice eproduktyserviceBean() {
    return mock(Eproduktyservice.class);
  }

  @MockBean(UserService.class)
  UserService userServiceBean() { return mock(UserService.class); }

}