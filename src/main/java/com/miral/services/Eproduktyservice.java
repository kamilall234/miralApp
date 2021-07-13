package com.miral.services;

import com.miral.controller.dto.ProductDto;
import com.miral.dao.model.ProductDao;
import com.miral.dao.repository.ProductRepository;
import com.miral.services.domain.Product;
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

  public Eproduktyservice(RxHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Product getProductyByGtInNumber(String gtinumber) {
    var productOptional = getProductFromDatabase(gtinumber);
    Product product;
    if (productOptional.isPresent()) {
      product = productOptional.get();
      logger.info("Product in database: " + product);
    } else {
      logger.info("Sending request to eproduktyAPI for barcode: {}", gtinumber);
      Flowable<ProductDto> response = httpClient.retrieve(HttpRequest.GET("products/get_products/?gtin_number="+ gtinumber),
          ProductDto.class);
      var productDto = response.blockingFirst();
      product = mapToProduct(saveProductToDatabase(productDto).orElseThrow());
    }

    return product;
  }

  private Optional<ProductDao> saveProductToDatabase(ProductDto productDto) {
    var product = new Product(productDto.getResults().getGtinNumber(), productDto.getResults().getName(), productDto.getResults().getUnit(),
        productDto.getResults().getNetVolume(), productDto.getResults().getBrand());
    return productRepository.saveNewProduct(product);
  }

  /*Should return not entity*/
  private Optional<Product> getProductFromDatabase(String gtinNumber) {
    logger.info("Trying to find product in database with barcode; " + gtinNumber);
    var productList = productRepository.findByGtinNumber(gtinNumber);
    var product = productList.isEmpty() ? null : mapToProduct(productList.stream().findFirst().get());

    return Optional.ofNullable(product);
  }

  private Product mapToProduct(ProductDao productDao) {
    return new Product(productDao.getGtinNumber(), productDao.getName(), productDao.getUnit(),
        productDao.getNetVolume(), productDao.getBrand());
  }
}
