package com.example.appliance.repositories;

import com.example.appliance.models.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {
    List<OrderInfo> findByOrderStatus(String orderStatus);
    List<OrderInfo> findByCustomer_IdCustomer(Integer idCustomer);
}