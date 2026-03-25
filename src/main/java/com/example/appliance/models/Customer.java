package com.example.appliance.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;

    @NotBlank(message = "Имя клиента не может быть пустым.")
    @Size(max = 50, message = "Имя клиента не должно превышать 50 символов.")
    private String firstName;

    @NotBlank(message = "Фамилия клиента не может быть пустой.")
    @Size(max = 50, message = "Фамилия клиента не должна превышать 50 символов.")
    private String lastName;

    @Size(max = 50, message = "Отчество клиента не должно превышать 50 символов.")
    private String patronymic;

    @NotBlank(message = "Телефон не может быть пустым.")
    @Size(max = 20, message = "Телефон не должен превышать 20 символов.")
    private String phoneNumber;

    @NotBlank(message = "Адрес не может быть пустым.")
    @Size(max = 200, message = "Адрес не должен превышать 200 символов.")
    private String address;

    @ManyToOne
    @JoinColumn(name = "account_ID")
    private Account account;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderInfo> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;
}