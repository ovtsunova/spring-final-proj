package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Customer;
import com.example.appliance.services.CurrentUserService;
import com.example.appliance.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/profile")
@PreAuthorize("hasRole('USER')")
public class UserProfileController {

    private final CurrentUserService currentUserService;
    private final CustomerService customerService;

    public UserProfileController(CurrentUserService currentUserService,
                                 CustomerService customerService) {
        this.currentUserService = currentUserService;
        this.customerService = customerService;
    }

    @GetMapping
    public String profilePage(Model model) {
        model.addAttribute("customer", currentUserService.getCurrentCustomer());
        return "user/profile/view";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model) {
        model.addAttribute("customer", currentUserService.getCurrentCustomer());
        return "user/profile/form";
    }

    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute("customer") Customer customer,
                                BindingResult bindingResult) {
        Customer currentCustomer = currentUserService.getCurrentCustomer();

        if (bindingResult.hasErrors()) {
            return "user/profile/form";
        }

        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setPatronymic(customer.getPatronymic());
        currentCustomer.setPhoneNumber(customer.getPhoneNumber());
        currentCustomer.setAddress(customer.getAddress());

        customerService.save(currentCustomer);
        return "redirect:/profile";
    }
}