package com.example.appliance.services;

import com.example.appliance.models.Customer;
import com.example.appliance.repositories.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final CustomerRepository customerRepository;

    public CurrentUserService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String getCurrentLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Customer getCurrentCustomer() {
        String login = getCurrentLogin();
        return customerRepository.findByAccount_UserLogin(login)
                .orElseThrow(() -> new RuntimeException("Клиент текущего пользователя не найден"));
    }
}