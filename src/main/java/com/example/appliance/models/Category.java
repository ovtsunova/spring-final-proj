package com.example.appliance.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @NotBlank(message = "Название категории не может быть пустым.")
    @Size(max = 100, message = "Название категории не должно превышать 100 символов.")
    @Column(nullable = false, unique = true)
    private String categoryName;

    @Size(max = 500, message = "Описание категории не должно превышать 500 символов.")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> products;
}