package com.miral.dao.repository;

import com.miral.dao.model.ProductDao;
import com.miral.services.domain.Product;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Repository
public abstract class ProductRepository implements CrudRepository<ProductDao, Long> {
  private final EntityManager entityManager;

  public ProductRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public abstract List<ProductDao> findByGtinNumber(String gtinNumber);

  //@Transactional
  public Optional<ProductDao> saveNewProduct(Product product) {
    var productDao = new ProductDao(product.getGtinNumber(), product.getName(),
        product.getUnit(), product.getNetVolume(), product.getBrand());
    return Optional.of(save(productDao));
  }
}
