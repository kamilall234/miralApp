package com.miral.products.exception.exceptions;

public class ProductNotFoundExceeption extends RuntimeException {

  public ProductNotFoundExceeption(String gtinNumber) {
    super(String.format("Product with id not exists in database %s", gtinNumber));
  }
}
