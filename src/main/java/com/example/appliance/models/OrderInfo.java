package com.example.appliance.models;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Orders")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @NotBlank(message = "Дата заказа не может быть пустой.")
    @Size(max = 30, message = "Дата заказа не должна превышать 30 символов.")
    private String orderDate;

    @NotBlank(message = "Статус заказа не может быть пустым.")
    @Size(max = 30, message = "Статус заказа не должен превышать 30 символов.")
    private String orderStatus;

    @NotNull(message = "Сумма заказа должна быть указана.")
    @Positive(message = "Сумма заказа должна быть больше нуля.")
    private BigDecimal totalAmount;

    @NotBlank(message = "Адрес доставки не может быть пустым.")
    @Size(max = 255, message = "Адрес доставки не должен превышать 255 символов.")
    private String deliveryAddress;

    @Size(max = 500, message = "Комментарий не должен превышать 500 символов.")
    private String comment;

    @NotNull(message = "Клиент должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "customer_ID")
    private Customer customer;

    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "orderInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Payment payment;
}