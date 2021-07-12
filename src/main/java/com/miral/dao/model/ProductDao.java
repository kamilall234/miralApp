package com.miral.dao.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Immutable;


@Entity(name = "Product")
@Introspected
@Immutable
public class ProductDao {
  @Id
  private String gtinNumber;
  private String name;
  private String unit;
  private float netVolume;

  public ProductDao() {
  }

  @Creator
  public ProductDao(String gtinNumber, String name, String unit, float netVolume) {
    this.gtinNumber = gtinNumber;
    this.name = name;
    this.unit = unit;
    this.netVolume = netVolume;
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

}
