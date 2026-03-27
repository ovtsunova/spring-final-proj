package com.example.appliance.services;

import com.example.appliance.models.OrderInfo;
import com.example.appliance.repositories.OrderInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoService {

    private final OrderInfoRepository orderInfoRepository;

    public OrderInfoService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    public List<OrderInfo> findAll() {
        return orderInfoRepository.findAll();
    }

    public OrderInfo findById(Integer id) {
        return orderInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден."));
    }

    public List<OrderInfo> findByCustomerId(Integer customerId) {
        return orderInfoRepository.findByCustomer_IdCustomer(customerId);
    }

    public OrderInfo save(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }

    public void deleteById(Integer id) {
        orderInfoRepository.deleteById(id);
    }
}