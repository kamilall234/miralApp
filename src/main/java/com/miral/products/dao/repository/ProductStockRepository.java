package com.miral.products.dao.repository;

import com.miral.products.dao.model.Delivery;
import com.miral.products.dao.model.Product;
import com.miral.products.dao.model.ProductStock;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStockRepository extends CrudRepository<ProductStock, Long> {
    List<ProductStock> findByDelivery(Delivery delivery);

    Optional<ProductStock> findByProductAndDelivery(Product product, Delivery delivery);
}
