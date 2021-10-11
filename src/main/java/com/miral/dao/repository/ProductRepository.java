package com.miral.dao.repository;

import com.miral.dao.model.Product;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import javax.persistence.EntityManager;


@Repository
public abstract class ProductRepository implements CrudRepository<Product, Long> {
  private final EntityManager entityManager;

  public ProductRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public abstract List<Product> findByGtinNumber(String gtinNumber);

}
