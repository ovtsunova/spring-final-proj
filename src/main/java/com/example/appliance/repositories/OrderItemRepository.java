package com.example.appliance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appliance.models.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderInfo_IdOrder(Integer idOrder);
    List<OrderItem> findByProduct_IdProduct(Integer idProduct);
}