package com.miral.products.services;


import com.miral.products.controller.dto.DeliveryDto;
import com.miral.products.controller.dto.ProductDeliveryDto;
import com.miral.products.controller.dto.ProductsDeliveryDto;
import com.miral.products.dao.mapper.DeliveryMapper;
import com.miral.products.dao.mapper.ProductStockMapper;
import com.miral.products.dao.model.Delivery;
import com.miral.products.dao.model.DeliveryStatus;
import com.miral.products.dao.model.ProductStock;
import com.miral.products.dao.repository.DeliveryRepository;
import com.miral.products.dao.repository.ProductStockRepository;
import com.miral.products.exception.exceptions.DeliveryNotFoundException;
import com.miral.products.exception.exceptions.ProductNotFoundExceeption;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DeliveryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);
  private final DeliveryRepository deliveryRepository;
  private final ProductStockRepository productStockRepository;
  private final DeliveryMapper deliveryMapper;
  private final ProductStockMapper productStockMapper;
  private final Eproduktyservice productService;

  public DeliveryService(DeliveryRepository deliveryRepository,
                         ProductStockRepository productStockRepository,
                         Eproduktyservice productService) {
    this.deliveryRepository = deliveryRepository;
    this.productStockRepository = productStockRepository;
    this.productService = productService;
    this.deliveryMapper = Mappers.getMapper(DeliveryMapper.class);
    this.productStockMapper = Mappers.getMapper(ProductStockMapper.class);
  }

  public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
    var newDelivery = deliveryMapper.mapToDelivery(deliveryDto);
    newDelivery.changeStatus(DeliveryStatus.ACTIVE.name());
    return deliveryMapper.mapToDeliveryDto(deliveryRepository.save(newDelivery));
  }

  public boolean changeStatusDelivery(Long id, String status) {
    var existingDelivery =
        deliveryRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException(String.valueOf(id)));

    var newStatus = DeliveryStatus.valueOf(status);
    var previousStatus = existingDelivery.getStatus();
    existingDelivery.changeStatus(newStatus.name());
    LOGGER.debug("Status changed for delivery from {}, {}", previousStatus, existingDelivery);

    return true;
  }

  @Transactional
  public boolean addProductToStock(ProductsDeliveryDto productsDeliveryDto) {
    var deliveryId = productsDeliveryDto.deliveryId();
    if (isDeliveryActive(deliveryId)) {
      var delivery = deliveryRepository.findById(deliveryId).get();
      var updatedProductsInStock = updateProductsInDelivery(productsDeliveryDto.productsInDelivery(), delivery);
      return productsDeliveryDto.productsInDelivery()
          .stream()
          .filter(productDeliveryDto -> updatedProductsInStock.stream().noneMatch(productStock -> productStock.getProduct().getGtinNumber().equals(productDeliveryDto.gtinNumber())))
          .map(p -> new ProductStock(delivery,
              productService.getProductByGtinNumber(p.gtinNumber()).orElseThrow(() -> new ProductNotFoundExceeption(p.gtinNumber())),
              p.count(), p.priceStockNet(), p.priceSellingNet(), p.priceSellingGross()))
          .map(productStockRepository::save).toList().isEmpty();
    } else {
      return false;
    }
  }

  @Transactional
  public boolean updateProductToStock(ProductsDeliveryDto productsDeliveryDto) {
    var deliveryId = productsDeliveryDto.deliveryId();

    if (isDeliveryActive(deliveryId)) {
      var delivery = deliveryRepository.findById(deliveryId).get();
      var products = productsDeliveryDto.productsInDelivery();
      return  updateProductsInDelivery(products, delivery).isEmpty();
    }
    return false;
  }

  private List<ProductStock> updateProductsInDelivery(List<ProductDeliveryDto> productsToUpdate, Delivery delivery) throws ProductNotFoundExceeption{
    return  productsToUpdate.stream()
        .map(p -> productService.getProductByGtinNumber(p.gtinNumber()).orElseThrow(() -> new ProductNotFoundExceeption(p.gtinNumber())))
        .map(product -> productStockRepository.findByProductAndDelivery(product, delivery).orElse(null))
        .filter(Objects::nonNull)
        .peek(productStock -> {
          var productUpdate =
              productsToUpdate.stream().filter(p -> p.gtinNumber().equals(productStock.getProduct().getGtinNumber())).findFirst().get();
          productStock.changeValues(productUpdate.count(), productStock.getPriceStockNet(), productUpdate.priceSellingNet(),
              productUpdate.priceSellingGross());
        }).map(productStockRepository::update).toList();
  }

  public List<ProductDeliveryDto> getStockForDeliveryWithId(Long id) {
    var delivery = deliveryRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException(String.valueOf(id)));
    var productsForDeliveryWithId = productStockRepository.findByDelivery(delivery);

    return productStockMapper.mapToProductsDeliveryDtos(productsForDeliveryWithId);
  }

  private boolean isDeliveryActive(Long deliveryId) {
    return deliveryRepository.findById(deliveryId).orElseThrow(() -> new DeliveryNotFoundException(String.valueOf(deliveryId)))
        .getStatus().equals(DeliveryStatus.ACTIVE.name());
  }
}
