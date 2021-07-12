package com.miral.controller.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class ProductDto {
  private Integer count;

  private Results results;

  @JsonCreator
  public ProductDto(@JsonProperty("count") Integer count, @JsonProperty("results") Results results) {
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

    @JsonCreator
    public Results(@JsonProperty("gtinNumber") String gtinNumber, @JsonProperty("name") String name,
                   @JsonProperty("netVolume") Float netVolume, @JsonProperty("unit") String unit) {
      this.gtinNumber = gtinNumber;
      this.name = name;
      this.netVolume = netVolume;
      this.unit = unit;
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
  }

}
