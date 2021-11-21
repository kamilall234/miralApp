package com.miral.products.dao.model;


import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class Delivery {

  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  private String name;
  @NotEmpty
  private String shopName;

  @NotEmpty
  private String type;

  @NotEmpty
  private String status;

  private ZonedDateTime deliveryDate;

  protected Delivery() {
  }

  public Delivery(Long id, String name, String shopName, String type, String status) {
    this.id = id;
    this.name = name;
    this.shopName = shopName;
    this.type = type;
    this.status = status;
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

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public void changeStatus(String status) {
    this.status = status;
  }
}
