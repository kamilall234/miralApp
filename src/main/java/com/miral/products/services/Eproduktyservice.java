package com.miral.products.services;

import com.miral.products.controller.dto.ProductDto;
import com.miral.products.controller.dto.ProductFromEserviceDto;
import com.miral.products.dao.mapper.ProductMapper;
import com.miral.products.dao.model.Product;
import com.miral.products.dao.repository.ProductRepository;
import com.miral.products.exception.exceptions.ProductNotFoundExceeption;
import com.miral.products.exception.exceptions.ProductSaveException;
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
import reactor.core.publisher.Mono;

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

  public ProductDto getProductDtoByGtInNumber(String gtinumber) {
    var productOptional = getProductByGtinNumber(gtinumber);
    ProductDto productDto;
    if (productOptional.isPresent()) {
      productDto = productMapper.mapToProductDto(productOptional.get());
    } else {
      Mono<ProductFromEserviceDto> response =
          Mono.from(httpClient.retrieve(HttpRequest.GET("products/get_products/?gtin_number=" + gtinumber),
              ProductFromEserviceDto.class));
      logger.debug("Response from httpClient for product with gtinNumber {} successfull ", gtinumber);
      var productFromEserviceDto =
          response.blockOptional().orElseThrow(() -> new ProductNotFoundExceeption("Product not found in eproduktyservice"));
      productDto = saveProductToDatabase(productFromEserviceDto);
    }

    return productDto;
  }

  public ProductDto saveNewProductToDatabase(ProductDto productDto) {
    var product = productMapper.mapToProduct(productDto);

    return Optional.ofNullable(productMapper.mapToProductDto(productRepository.save(product)))
        .orElseThrow(() -> new ProductSaveException(String.format("Save product error: %s", productDto)));
  }

  private ProductDto saveProductToDatabase(ProductFromEserviceDto productFromEserviceDto) {
    var product = productMapper.mapToProduct(productFromEserviceDto);

    return Optional.ofNullable(productMapper.mapToProductDto(productRepository.save(product))).orElseThrow(
        () -> new ProductSaveException(String.format("Save product error: %s", productFromEserviceDto)));
  }

  public Optional<Product> getProductByGtinNumber(String gtinNumber) {
    var productList = productRepository.findByGtinNumber(gtinNumber);
    var product = productList.stream().findFirst();

    return product.or(Optional::empty);
  }

}
