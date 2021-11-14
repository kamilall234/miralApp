package com.miral.products.controller;

import com.miral.products.controller.dto.ProductDto;
import com.miral.products.services.Eproduktyservice;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(ProductStore.PRODUCT)
public class ProductStore {
  private final Logger logger = LoggerFactory.getLogger(ProductStore.class);
  static final String PRODUCT = "/product";

  @Inject
  private Eproduktyservice eproduktyservice;

  @Get
  public HttpResponse<ProductDto> checkProductByGtinNumber(@QueryValue String gtinNumber) {
    var product = eproduktyservice.getProductyByGtInNumber(gtinNumber);

    return HttpResponse.ok(product);
  }

  @Post
  public HttpResponse<ProductDto> writeDownNewProduct(@Body ProductDto productDto) {
    return HttpResponse.ok(eproduktyservice.saveNewProductToDatabase(productDto));
  }
}
