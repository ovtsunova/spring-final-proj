package com.example.appliance.services;

import com.example.appliance.dto.RegisterForm;
import com.example.appliance.models.Account;
import com.example.appliance.models.Customer;
import com.example.appliance.models.Role;
import com.example.appliance.repositories.AccountRepository;
import com.example.appliance.repositories.CustomerRepository;
import com.example.appliance.repositories.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AccountRepository accountRepository,
                       CustomerRepository customerRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterForm form) {
        if (accountRepository.existsByUserLogin(form.getUserLogin())) {
            throw new RuntimeException("Пользователь с таким логином уже существует.");
        }

        if (accountRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует.");
        }

        if (!form.getUserPassword().equals(form.getConfirmPassword())) {
            throw new RuntimeException("Пароль и подтверждение пароля не совпадают.");
        }

        Role userRole = roleRepository.findByRoleLabel("USER")
                .orElseThrow(() -> new RuntimeException("Роль USER не найдена."));

        Account account = new Account();
        account.setUserLogin(form.getUserLogin());
        account.setUserPassword(passwordEncoder.encode(form.getUserPassword()));
        account.setEmail(form.getEmail());
        account.setRole(userRole);

        accountRepository.save(account);

        Customer customer = new Customer();
        customer.setFirstName(form.getFirstName());
        customer.setLastName(form.getLastName());
        customer.setPatronymic(form.getPatronymic());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setAddress(form.getAddress());
        customer.setAccount(account);

        customerRepository.save(customer);
    }
}