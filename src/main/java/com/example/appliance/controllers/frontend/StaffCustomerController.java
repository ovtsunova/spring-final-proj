package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Customer;
import com.example.appliance.services.AccountService;
import com.example.appliance.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/customers")
public class StaffCustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    public StaffCustomerController(CustomerService customerService,
                                   AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "staff/customers/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String viewCustomer(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "staff/customers/view";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("formTitle", "Добавление клиента");
        return "staff/customers/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createCustomer(@Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult,
                                 @RequestParam("accountId") Integer accountId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAll());
            model.addAttribute("formTitle", "Добавление клиента");
            return "staff/customers/form";
        }

        customer.setAccount(accountService.findById(accountId));
        customerService.save(customer);
        return "redirect:/staff/customers";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("formTitle", "Редактирование клиента");
        return "staff/customers/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCustomer(@PathVariable Integer id,
                                 @Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult,
                                 @RequestParam("accountId") Integer accountId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAll());
            model.addAttribute("formTitle", "Редактирование клиента");
            return "staff/customers/form";
        }

        customer.setIdCustomer(id);
        customer.setAccount(accountService.findById(accountId));
        customerService.save(customer);
        return "redirect:/staff/customers";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
        return "redirect:/staff/customers";
    }
}