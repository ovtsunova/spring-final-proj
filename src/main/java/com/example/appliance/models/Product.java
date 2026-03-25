package com.example.appliance.models;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @NotBlank(message = "Название товара не может быть пустым.")
    @Size(max = 150, message = "Название товара не должно превышать 150 символов.")
    private String productName;

    @NotBlank(message = "Модель товара не может быть пустой.")
    @Size(max = 100, message = "Модель товара не должна превышать 100 символов.")
    private String model;

    @Size(max = 1000, message = "Описание товара не должно превышать 1000 символов.")
    private String description;

    @NotNull(message = "Цена должна быть указана.")
    @Positive(message = "Цена должна быть больше нуля.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Количество товара должно быть указано.")
    @PositiveOrZero(message = "Количество товара не может быть отрицательным.")
    private Integer stockQuantity;

    @NotNull(message = "Срок гарантии должен быть указан.")
    @PositiveOrZero(message = "Срок гарантии не может быть отрицательным.")
    private Integer warrantyMonths;

    @Size(max = 50, message = "Цвет не должен превышать 50 символов.")
    private String color;

    @Size(max = 255, message = "Ссылка на изображение не должна превышать 255 символов.")
    private String imageUrl;

    @NotBlank(message = "Статус доступности должен быть указан.")
    @Size(max = 30, message = "Статус доступности не должен превышать 30 символов.")
    private String availabilityStatus;

    @NotNull(message = "Категория обязательна.")
    @ManyToOne
    @JoinColumn(name = "category_ID")
    private Category category;

    @NotNull(message = "Бренд обязателен.")
    @ManyToOne
    @JoinColumn(name = "brand_ID")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;
}