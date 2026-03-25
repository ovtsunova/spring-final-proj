package com.example.appliance.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccount;

    @NotBlank(message = "Логин не может быть пустым.")
    @Size(min = 3, max = 20, message = "Логин должен быть от 3 до 20 символов.")
    @Column(nullable = false, unique = true)
    private String userLogin;

    @NotBlank(message = "Пароль не может быть пустым.")
    @Size(min = 8, message = "Пароль должен быть минимум 8 символов.")
    @Pattern(regexp = ".*[A-Z].*", message = "Пароль должен содержать хотя бы одну заглавную букву.")
    @Pattern(regexp = ".*[a-z].*", message = "Пароль должен содержать хотя бы одну строчную букву.")
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру.")
    @Pattern(regexp = ".*[!@#$%^&*().,?].*", message = "Пароль должен содержать специальный символ (!@#$%^&*).")
    @Column(nullable = false)
    private String userPassword;

    @Email(message = "Некорректный email.")
    @NotBlank(message = "Email не может быть пустым.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Роль должна быть указана.")
    @ManyToOne
    @JoinColumn(name = "role_ID")
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Customer> customers;
}