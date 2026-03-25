package com.example.appliance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appliance.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderInfo_IdOrder(Integer idOrder);
    Optional<Payment> findByPaymentStatus(String paymentStatus);
}