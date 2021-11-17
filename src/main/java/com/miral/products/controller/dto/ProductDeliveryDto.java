package com.miral.products.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductDeliveryDto(String gtinNumber, Integer count, Long priceStockNet,
                                 Long priceSellingNet, Long priceSellingGross) {


}
