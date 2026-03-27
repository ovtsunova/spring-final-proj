package com.example.appliance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Имя не может быть пустым.")
    @Size(max = 50, message = "Имя не должно превышать 50 символов.")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой.")
    @Size(max = 50, message = "Фамилия не должна превышать 50 символов.")
    private String lastName;

    @Size(max = 50, message = "Отчество не должно превышать 50 символов.")
    private String patronymic;

    @NotBlank(message = "Телефон не может быть пустым.")
    @Size(max = 20, message = "Телефон не должен превышать 20 символов.")
    private String phoneNumber;

    @NotBlank(message = "Адрес не может быть пустым.")
    @Size(max = 200, message = "Адрес не должен превышать 200 символов.")
    private String address;

    @NotBlank(message = "Логин не может быть пустым.")
    @Size(min = 3, max = 20, message = "Логин должен быть от 3 до 20 символов.")
    private String userLogin;

    @NotBlank(message = "Пароль не может быть пустым.")
    @Size(min = 8, message = "Пароль должен быть минимум 8 символов.")
    @Pattern(regexp = ".*[A-Z].*", message = "Пароль должен содержать хотя бы одну заглавную букву.")
    @Pattern(regexp = ".*[a-z].*", message = "Пароль должен содержать хотя бы одну строчную букву.")
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру.")
    @Pattern(regexp = ".*[!@#$%^&*().,?].*", message = "Пароль должен содержать специальный символ.")
    private String userPassword;

    @NotBlank(message = "Подтверждение пароля не может быть пустым.")
    private String confirmPassword;

    @Email(message = "Некорректный email.")
    @NotBlank(message = "Email не может быть пустым.")
    private String email;
}