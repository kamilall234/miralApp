package com.miral.services;

import com.miral.controller.dto.ProductDto;
import com.miral.controller.dto.ProductFromEserviceDto;
import com.miral.dao.mapper.ProductMapper;
import com.miral.dao.repository.ProductRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

@Singleton
@Requires(property = "eprodukty_api")
public class Eproduktyservice {
  private final Logger logger = LoggerFactory.getLogger(Eproduktyservice.class);

  @Client("${eprodukty_api}")
  @Inject
  private HttpClient httpClient;

  @Inject
  private ProductRepository productRepository;

  private ProductMapper productMapper;

  public Eproduktyservice(HttpClient httpClient) {
    this.httpClient = httpClient;
    this.productMapper = Mappers.getMapper(ProductMapper.class);
  }

  public ProductDto getProductyByGtInNumber(String gtinumber) {
    var productOptional = getProductFromDatabase(gtinumber);
    ProductDto productDto;
    if (productOptional.isPresent()) {
      productDto = productOptional.get();
      logger.debug(String.format("Product in database: %s", productDto));
    } else {
      logger.debug(String .format("Sending request to eproduktyAPI for barcode: %s", gtinumber));

      Flux<ProductFromEserviceDto> response =
          Flux.from(httpClient.retrieve(HttpRequest.GET("products/get_products/?gtin_number=" + gtinumber),
              ProductFromEserviceDto.class));
      var productFromEserviceDto = response.blockFirst();
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
    logger.debug(String.format("Trying to find product in database with barcode; %s", gtinNumber));
    var productList = productRepository.findByGtinNumber(gtinNumber);
    var product = productList.stream().findFirst().stream().map(p -> productMapper.mapToProductDto(p)).findFirst();

    return product.or(Optional::empty);
  }

}
