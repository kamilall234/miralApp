package com.miral.products.dao.mapper;

import com.miral.products.controller.dto.DeliveryDto;
import com.miral.products.dao.model.Delivery;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryMapper {

  DeliveryDto mapToDeliveryDto(Delivery delivery);

  List<DeliveryDto> mapToDeliveryDtos(List<Delivery> deliveries);

  Delivery mapToDelivery(DeliveryDto deliveryDto);
}
