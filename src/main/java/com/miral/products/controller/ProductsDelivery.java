package com.miral.products.controller;

import com.miral.products.controller.dto.DeliveryDto;
import com.miral.products.controller.dto.ProductDeliveryDto;
import com.miral.products.dao.mapper.DeliveryMapper;
import com.miral.products.dao.repository.DeliveryRepository;
import com.miral.products.services.DeliveryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.mapstruct.factory.Mappers;

@Controller("/delivery")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class ProductsDelivery {

  private final DeliveryService deliveryService;
  private final DeliveryRepository deliveryRepository;
  private final DeliveryMapper deliveryMapper;

  public ProductsDelivery(DeliveryService deliveryService, DeliveryRepository deliveryRepository) {
    this.deliveryService = deliveryService;
    this.deliveryRepository = deliveryRepository;
    this.deliveryMapper = Mappers.getMapper(DeliveryMapper.class);
  }

  @Get("/{id}")
  public HttpResponse<DeliveryDto> getDeliveryById(@PathVariable Long id) {
    return HttpResponse.ok(deliveryMapper.mapToDeliveryDto(deliveryRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("No delivery with id " + id))));
  }

  @Post
  public HttpResponse<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
    return HttpResponse.ok(deliveryService.createDelivery(deliveryDto));
  }
}
