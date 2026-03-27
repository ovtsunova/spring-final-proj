package com.example.appliance.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appliance.models.OrderItem;
import com.example.appliance.repositories.OrderItemRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Integer id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Позиция заказа не найдена."));
    }

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}