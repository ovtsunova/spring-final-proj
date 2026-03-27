package com.example.appliance.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBrand;

    @NotBlank(message = "Название бренда не может быть пустым.")
    @Size(max = 100, message = "Название бренда не должно превышать 100 символов.")
    @Column(nullable = false, unique = true)
    private String brandName;

    @Size(max = 100, message = "Страна бренда не должна превышать 100 символов.")
    private String country;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> products;
}