package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerPanelController {

    @GetMapping("/manager")
    public String managerHome() {
        return "manager/index";
    }

    @GetMapping("/manager/products")
    public String managerProductsRedirect() {
        return "redirect:/admin/products";
    }

    @GetMapping("/manager/categories")
    public String managerCategoriesRedirect() {
        return "redirect:/admin/categories";
    }

    @GetMapping("/manager/brands")
    public String managerBrandsRedirect() {
        return "redirect:/admin/brands";
    }

    @GetMapping("/manager/customers")
    public String managerCustomersRedirect() {
        return "redirect:/admin/customers";
    }

    @GetMapping("/manager/orders")
    public String managerOrdersRedirect() {
        return "redirect:/admin/orders";
    }

    @GetMapping("/manager/order-items")
    public String managerOrderItemsRedirect() {
        return "redirect:/admin/order-items";
    }

    @GetMapping("/manager/reviews")
    public String managerReviewsRedirect() {
        return "redirect:/admin/reviews";
    }

    @GetMapping("/manager/payments")
    public String managerPaymentsRedirect() {
        return "redirect:/admin/payments";
    }
}