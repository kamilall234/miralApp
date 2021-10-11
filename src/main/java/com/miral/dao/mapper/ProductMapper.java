package com.miral.dao.mapper;

import com.miral.controller.dto.ProductDto;
import com.miral.controller.dto.ProductFromEserviceDto;
import com.miral.dao.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

  @Mapping(source = "results.gtinNumber", target = "gtinNumber")
  @Mapping(source = "results.name", target = "name")
  @Mapping(source = "results.netVolume", target = "netVolume")
  @Mapping(source = "results.brand", target = "brand")
  @Mapping(source = "results.unit", target = "unit")
  Product mapToProduct(ProductFromEserviceDto productFromEserviceDto);

  Product mapToProduct(ProductDto productDto);

  ProductDto mapToProductDto(Product product);

}
