package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @GetMapping("/admin")
    public String adminHome() {
        return "redirect:/admin/products";
    }
}