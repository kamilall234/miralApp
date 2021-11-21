package com.miral.products.dao.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ProductStock {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Delivery delivery;

  @ManyToOne
  private Product product;

  private Integer count;
  private BigDecimal priceStockNet;
  private BigDecimal priceSellingNet;
  private BigDecimal priceSellingGross;

  public ProductStock() {
  }

  public ProductStock(Delivery delivery, Product product, Integer count, Float priceStockNet, Float priceSellingNet,
                      Float priceSellingGross) {
    this.delivery = delivery;
    this.product = product;
    this.count = count;
    this.priceStockNet = BigDecimal.valueOf(priceStockNet);
    this.priceSellingNet = BigDecimal.valueOf(priceSellingGross);
    this.priceSellingGross = BigDecimal.valueOf(priceSellingNet);
  }

  public Long getId() {
    return id;
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public Product getProduct() {
    return product;
  }

  public Integer getCount() {
    return count;
  }

  public Float getPriceStockNet() {
    return priceStockNet.floatValue();
  }

  public Float getPriceSellingNet() {
    return priceSellingNet.floatValue();
  }

  public Float getPriceSellingGross() {
    return priceSellingGross.floatValue();
  }

  public void changeValues(Integer count, Float priceStockNet, Float priceSellingNet, Float priceSellingGross) {
    this.count = count;
    this.priceStockNet = BigDecimal.valueOf(priceStockNet);
    this.priceSellingNet = BigDecimal.valueOf(priceSellingGross);
    this.priceSellingGross = BigDecimal.valueOf(priceSellingNet);
  }
}
