package com.miral.products.dao.mapper;

import com.miral.products.controller.dto.ProductDeliveryDto;
import com.miral.products.dao.model.ProductStock;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface ProductStockMapper {
  ProductDeliveryDto mapToProductDeliveryDto(ProductStock productStock);

  List<ProductDeliveryDto> mapToProductsDeliveryDtos(List<ProductStock> productStocks);
}
