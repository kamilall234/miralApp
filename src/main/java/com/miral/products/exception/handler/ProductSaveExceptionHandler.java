package com.miral.products.exception.handler;

import com.miral.products.exception.exceptions.ProductSaveException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ProductSaveException.class, ExceptionHandler.class})
public class ProductSaveExceptionHandler {
}
