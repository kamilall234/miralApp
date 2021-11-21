package com.miral.products.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductsDeliveryDto(@NotNull Long deliveryId, List<ProductDeliveryDto> productsInDelivery) {

}
