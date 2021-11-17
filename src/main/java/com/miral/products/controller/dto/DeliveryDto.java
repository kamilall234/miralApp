package com.miral.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;

public record DeliveryDto(String name, String shopName, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") ZonedDateTime deliveryDate, Long id)  {

}
