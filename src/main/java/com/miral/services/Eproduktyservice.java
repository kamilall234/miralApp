package com.miral.services;

import com.miral.controller.dto.ProductDto;
import com.miral.controller.dto.ProductFromEserviceDto;
import com.miral.dao.mapper.ProductMapper;
import com.miral.dao.model.Product;
import com.miral.dao.repository.ProductRepository;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Requires(property = "eprodukty_api")
public class Eproduktyservice {
  private final Logger logger = LoggerFactory.getLogger(Eproduktyservice.class);

  @Client("${eprodukty_api}")
  @Inject
  private RxHttpClient httpClient;

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductMapper productMapper;

  public Eproduktyservice(RxHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public ProductDto getProductyByGtInNumber(String gtinumber) {
    var productOptional = getProductFromDatabase(gtinumber);
    ProductDto productDto;
    if (productOptional.isPresent()) {
      productDto = productOptional.get();
      logger.info("Product in database: " + productDto);
    } else {
      logger.info("Sending request to eproduktyAPI for barcode: {}", gtinumber);
      Flowable<ProductFromEserviceDto> response = httpClient.retrieve(HttpRequest.GET("products/get_products/?gtin_number="+ gtinumber),
          ProductFromEserviceDto.class);
      var productFromEserviceDto = response.blockingFirst();
      productDto = saveProductToDatabase(productFromEserviceDto).orElseThrow();
    }

    return productDto;
  }

  public ProductDto saveNewProductToDatabase(ProductDto productDto) {
    var product = productMapper.mapToProduct(productDto);

    return Optional.ofNullable(productMapper.mapToProductDto(productRepository.save(product))).orElseThrow();
  }

  private Optional<ProductDto> saveProductToDatabase(ProductFromEserviceDto productFromEserviceDto) {
    var product = productMapper.mapToProduct(productFromEserviceDto);

    return Optional.ofNullable(productMapper.mapToProductDto(productRepository.save(product)));
  }

  /*Should return not entity*/
  private Optional<ProductDto> getProductFromDatabase(String gtinNumber) {
    logger.info(String.format("Trying to find product in database with barcode; %s", gtinNumber));
    var productList = productRepository.findByGtinNumber(gtinNumber);
    var product = productList.stream().findFirst().stream().map(p -> productMapper.mapToProductDto(p)).findFirst();

    return product.or(Optional::empty);
  }

}
