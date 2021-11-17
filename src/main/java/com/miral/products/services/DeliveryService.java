package com.miral.products.services;


import com.miral.products.controller.dto.DeliveryDto;
import com.miral.products.dao.mapper.DeliveryMapper;
import com.miral.products.dao.model.Delivery;
import com.miral.products.dao.repository.DeliveryRepository;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Singleton;
import org.mapstruct.factory.Mappers;

@Singleton
public class DeliveryService {
  private final DeliveryRepository deliveryRepository;
  private final DeliveryMapper deliveryMapper;

  public DeliveryService(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
    this.deliveryMapper = Mappers.getMapper(DeliveryMapper.class);
  }

  public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
    var newDelivery = deliveryMapper.mapToDelivery(deliveryDto);
    return deliveryMapper.mapToDeliveryDto(deliveryRepository.save(newDelivery));
  }
}
