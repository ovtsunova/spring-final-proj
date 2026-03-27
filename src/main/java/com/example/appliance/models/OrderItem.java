package com.example.appliance.models;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Order_Items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrderItem;

    @NotNull(message = "Количество должно быть указано.")
    @Positive(message = "Количество должно быть больше нуля.")
    private Integer quantity;

    @NotNull(message = "Цена должна быть указана.")
    @Positive(message = "Цена должна быть больше нуля.")
    private BigDecimal itemPrice;

    @NotNull(message = "Заказ должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "order_ID")
    private OrderInfo orderInfo;

    @NotNull(message = "Товар должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "product_ID")
    private Product product;
}