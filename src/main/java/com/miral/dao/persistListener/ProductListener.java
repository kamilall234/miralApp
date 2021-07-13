package com.miral.dao.persistListener;

import com.miral.dao.model.ProductDao;
import io.micronaut.context.annotation.Factory;
import io.micronaut.data.event.listeners.PostPersistEventListener;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class ProductListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);


  @Singleton
  PrePersistEventListener<ProductDao> beforeProductSave() {
    return (produkt) -> {
      LOGGER.debug("Inserting produkt: {} ", produkt);
      return true;
    };
  }

  @Singleton
  PostPersistEventListener<ProductDao> afterProductSave() {
    return produkt -> {
      LOGGER.debug("Produkt inserted to database: {}", produkt);
    };
  }
}
