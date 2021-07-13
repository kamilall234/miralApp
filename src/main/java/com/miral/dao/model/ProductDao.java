package com.miral.dao.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;


@Entity(name = "Product")
@Introspected
@Immutable
public class ProductDao {

  @Id
  @GeneratedValue
  private Long id;

  @NaturalId
  private String gtinNumber;
  private String name;
  private String unit;
  private float netVolume;
  private String brand;

  public ProductDao() {
  }

  @Creator
  public ProductDao(String gtinNumber, String name, String unit, float netVolume, String brand) {
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
    return brand;
  }

  public Long getId() {
    return id;
  }
}
