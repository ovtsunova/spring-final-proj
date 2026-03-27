package com.example.appliance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appliance.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserLogin(String userLogin);
    Optional<Account> findByEmail(String email);
    boolean existsByUserLogin(String userLogin);
    boolean existsByEmail(String email);
}