package com.miral.products.dao.mapper;

import com.miral.products.controller.dto.ProductDto;
import com.miral.products.controller.dto.ProductFromEserviceDto;
import com.miral.products.dao.model.Product;
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
