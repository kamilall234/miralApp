package com.miral.controller;

import com.miral.controller.dto.ProductDto;
import com.miral.services.Eproduktyservice;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(ProductStore.PRODUCT)
public class ProductStore {
  private final Logger logger = LoggerFactory.getLogger(ProductStore.class);
  static final String PRODUCT = "/product";

  @Inject
  private Eproduktyservice eproduktyservice;

  @Get
  public HttpResponse<ProductDto> nameOfProductStore(@QueryValue String gtinNumber) {
    var product = eproduktyservice.getProductyByGtInNumber(gtinNumber);
    var results = new ProductDto.Results(product.getGtinNumber(), product.getName(), product.getNetVolume(), product.getUnit());
    ProductDto productDto = new ProductDto(1, results);

    return HttpResponse.ok(productDto);
  }
}
