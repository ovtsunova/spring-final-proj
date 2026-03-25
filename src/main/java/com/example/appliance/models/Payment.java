package com.example.appliance.models;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPayment;

    @NotNull(message = "Сумма оплаты должна быть указана.")
    @Positive(message = "Сумма оплаты должна быть больше нуля.")
    private BigDecimal amount;

    @NotBlank(message = "Способ оплаты не может быть пустым.")
    @Size(max = 30, message = "Способ оплаты не должен превышать 30 символов.")
    private String paymentMethod;

    @NotBlank(message = "Статус оплаты не может быть пустым.")
    @Size(max = 30, message = "Статус оплаты не должен превышать 30 символов.")
    private String paymentStatus;

    @NotBlank(message = "Дата оплаты не может быть пустой.")
    @Size(max = 30, message = "Дата оплаты не должна превышать 30 символов.")
    private String paymentDate;

    @NotNull(message = "Заказ должен быть указан.")
    @OneToOne
    @JoinColumn(name = "order_ID", unique = true)
    private OrderInfo orderInfo;
}