package com.miral.products.exception.exceptions;

public class DeliveryNotFoundException extends IllegalArgumentException{

  public DeliveryNotFoundException(String id) {
    super(String.format("Delivery with id not found %s", id));
  }
}
