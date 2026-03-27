package com.example.appliance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
@Table(name = "Cart_Items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCartItem;

    @NotNull
    @Positive
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_ID", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_ID", nullable = false)
    private Product product;
}