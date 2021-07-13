package com.miral.services.domain;

public class Product {
  private final String gtinNumber;
  private final String name;
  private final String unit;
  private final float netVolume;
  private final String brand;

  public Product(String gtinNumber, String name, String unit, float netVolume, String brand) {
    this.gtinNumber = gtinNumber;
    this.name = name;
    this.unit = unit;
    this.netVolume = netVolume;
    this.brand = brand;
  }

  public String getGtinNumber() {
    return gtinNumber;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public float getNetVolume() {
    return netVolume;
  }

  public String getBrand() {
    return this.brand;
  }
}
