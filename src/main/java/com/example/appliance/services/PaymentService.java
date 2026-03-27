package com.example.appliance.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appliance.models.Payment;
import com.example.appliance.repositories.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Оплата не найдена."));
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deleteById(Integer id) {
        paymentRepository.deleteById(id);
    }
}