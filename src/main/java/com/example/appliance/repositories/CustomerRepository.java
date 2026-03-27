package com.example.appliance.repositories;

import com.example.appliance.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    Optional<Customer> findByAccount_UserLogin(String userLogin);
}