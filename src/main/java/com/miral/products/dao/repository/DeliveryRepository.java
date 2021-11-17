package com.miral.products.dao.repository;

import com.miral.products.dao.model.Delivery;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

  Delivery findByName(String name);
}
