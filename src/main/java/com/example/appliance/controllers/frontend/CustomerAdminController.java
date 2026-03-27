package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.appliance.models.Customer;
import com.example.appliance.services.AccountService;
import com.example.appliance.services.CustomerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/customers")
public class CustomerAdminController {

    private final CustomerService customerService;
    private final AccountService accountService;

    public CustomerAdminController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin/customers/list";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "admin/customers/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("formTitle", "Добавление клиента");
        return "admin/customers/form";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult,
                                 @RequestParam("accountId") Integer accountId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAll());
            model.addAttribute("formTitle", "Добавление клиента");
            return "admin/customers/form";
        }

        customer.setAccount(accountService.findById(accountId));
        customerService.save(customer);
        return "redirect:/admin/customers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("formTitle", "Редактирование клиента");
        return "admin/customers/form";
    }

    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable Integer id,
                                 @Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult,
                                 @RequestParam("accountId") Integer accountId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAll());
            model.addAttribute("formTitle", "Редактирование клиента");
            return "admin/customers/form";
        }

        customer.setIdCustomer(id);
        customer.setAccount(accountService.findById(accountId));
        customerService.save(customer);
        return "redirect:/admin/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
        return "redirect:/admin/customers";
    }
}