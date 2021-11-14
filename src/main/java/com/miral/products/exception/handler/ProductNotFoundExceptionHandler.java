package com.miral.products.exception.handler;

import com.miral.products.exception.exceptions.ProductNotFoundExceeption;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ProductNotFoundExceeption.class, ExceptionHandler.class})
public class ProductNotFoundExceptionHandler implements ExceptionHandler<ProductNotFoundExceeption, HttpResponse>{

  private final ErrorResponseProcessor<?> errorResponseProcessor;

  public ProductNotFoundExceptionHandler(ErrorResponseProcessor<?> errorResponseProcessor) {
    this.errorResponseProcessor = errorResponseProcessor;
  }

  @Override
  public HttpResponse handle(HttpRequest request, ProductNotFoundExceeption exception) {
    return errorResponseProcessor.processResponse(ErrorContext.builder(request)
        .cause(exception).errorMessage(exception.getMessage()).build(), HttpResponse.badRequest());
  }
}
