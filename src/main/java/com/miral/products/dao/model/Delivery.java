package com.miral.products.dao.model;


import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class Delivery {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String shopName;
  private ZonedDateTime deliveryDate;

  protected Delivery() {
  }

  public Delivery(String name, String shopName) {
    this.name = name;
    this.shopName = shopName;
    this.deliveryDate = ZonedDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getShopName() {
    return shopName;
  }

  public ZonedDateTime getDeliveryDate() {
    return deliveryDate;
  }
}
