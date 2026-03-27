package com.example.appliance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

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

    // Используем LocalDate для даты
    @NotNull(message = "Дата отзыва не может быть пустой.")
    private LocalDate reviewDate;

    @NotNull(message = "Клиент должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "customer_ID")
    private Customer customer;

    @NotNull(message = "Товар должен быть указан.")
    @ManyToOne
    @JoinColumn(name = "product_ID")
    private Product product;

    @PrePersist
    public void setReviewDate() {
        if (this.reviewDate == null) {
            this.reviewDate = LocalDate.now();
        }
    }
}