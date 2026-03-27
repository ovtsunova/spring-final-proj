package com.example.appliance.services;

import com.example.appliance.models.Account;
import com.example.appliance.repositories.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Аккаунт не найден"));
    }

    public Account create(Account account) {
        if (account.getUserPassword() == null || account.getUserPassword().trim().isEmpty()) {
            throw new RuntimeException("Пароль не может быть пустым");
        }

        account.setUserPassword(passwordEncoder.encode(account.getUserPassword()));
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        Account existingAccount = findById(account.getIdAccount());

        existingAccount.setUserLogin(account.getUserLogin());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setRole(account.getRole());

        if (account.getUserPassword() != null && !account.getUserPassword().trim().isEmpty()) {
            existingAccount.setUserPassword(passwordEncoder.encode(account.getUserPassword()));
        }

        return accountRepository.save(existingAccount);
    }

    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }
}