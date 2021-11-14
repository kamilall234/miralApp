package com.miral.products.controller.dto;

public class ProductDto {
  private final String gtinNumber;
  private final String name;
  private final Float netVolume;
  private final String unit;
  private final String brand;

  public ProductDto(String gtinNumber, String name, Float netVolume, String unit, String brand) {
    this.gtinNumber = gtinNumber;
    this.name = name;
    this.netVolume = netVolume;
    this.unit = unit;
    this.brand = brand;
  }

  public String getGtinNumber() {
    return gtinNumber;
  }

  public String getName() {
    return name;
  }

  public Float getNetVolume() {
    return netVolume;
  }

  public String getUnit() {
    return unit;
  }

  public String getBrand() {
    return brand;
  }
}
