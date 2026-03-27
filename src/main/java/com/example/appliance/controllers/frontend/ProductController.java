package com.example.appliance.controllers.frontend;

import com.example.appliance.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "store/product-view";
    }
}