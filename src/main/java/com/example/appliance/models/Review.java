package com.example.appliance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @NotNull(message = "Оценка должна быть указана.")
    @Min(value = 1, message = "Оценка должна быть не меньше 1.")
    @Max(value = 5, message = "Оценка должна быть не больше 5.")
    private Integer rating;

    @NotBlank(message = "Комментарий не может быть пустым.")
    @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов.")
    private String comment;

    @NotBlank(message = "Дата отзыва не может быть пустой.")
    @Size(max = 30, message = "Дата отзыва не должна превышать 30 символов.")
    private String reviewDate;

    @NotNull(message = "Клиент должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "customer_ID")
    private Customer customer;

    @NotNull(message = "Товар должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "product_ID")
    private Product product;
}