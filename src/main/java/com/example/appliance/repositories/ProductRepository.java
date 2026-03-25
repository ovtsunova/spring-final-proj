package com.example.appliance.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appliance.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByBrand_BrandName(String brandName);
    List<Product> findByCategory_CategoryName(String categoryName);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product> findByAvailabilityStatus(String availabilityStatus);
}