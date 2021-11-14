package com.miral.products.controller.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties()
public class ProductFromEserviceDto {
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Integer count;

  private Results results;

  @JsonCreator
  public ProductFromEserviceDto(@JsonProperty("count") Integer count, @JsonProperty("results") Results results) {
    this.count = count;
    this.results = results;
  }

  public Integer getCount() {
    return count;
  }

  public Results getResults() {
    return this.results;
  }

  public static class Results {
    private String gtinNumber;
    private String name;
    private Float netVolume;
    private String unit;
    private String brand;

    @JsonCreator
    public Results(@JsonProperty("gtinNumber") String gtinNumber, @JsonProperty("name") String name,
                   @JsonProperty("netVolume") Float netVolume, @JsonProperty("unit") String unit,
                   @JsonProperty("brand") String brand) {
      this.gtinNumber = gtinNumber;
      this.name = name;
      this.netVolume = netVolume;
      this.unit = unit;
      this.brand = brand;
    }

    public String getName() {
      return this.name;
    }

    public String getGtinNumber() {
      return gtinNumber;
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

}
