package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.appliance.models.OrderInfo;
import com.example.appliance.services.CustomerService;
import com.example.appliance.services.OrderInfoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/orders")
public class OrderInfoAdminController {

    private final OrderInfoService orderInfoService;
    private final CustomerService customerService;

    public OrderInfoAdminController(OrderInfoService orderInfoService, CustomerService customerService) {
        this.orderInfoService = orderInfoService;
        this.customerService = customerService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderInfoService.findAll());
        return "admin/orders/list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderInfoService.findById(id));
        return "admin/orders/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("order", new OrderInfo());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("formTitle", "Добавление заказа");
        return "admin/orders/form";
    }

    @PostMapping
    public String createOrder(@Valid @ModelAttribute("order") OrderInfo order,
                              BindingResult bindingResult,
                              @RequestParam("customerId") Integer customerId,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("formTitle", "Добавление заказа");
            return "admin/orders/form";
        }

        order.setCustomer(customerService.findById(customerId));
        orderInfoService.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderInfoService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("formTitle", "Редактирование заказа");
        return "admin/orders/form";
    }

    @PostMapping("/{id}")
    public String updateOrder(@PathVariable Integer id,
                              @Valid @ModelAttribute("order") OrderInfo order,
                              BindingResult bindingResult,
                              @RequestParam("customerId") Integer customerId,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("formTitle", "Редактирование заказа");
            return "admin/orders/form";
        }

        order.setIdOrder(id);
        order.setCustomer(customerService.findById(customerId));
        orderInfoService.save(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Integer id) {
        orderInfoService.deleteById(id);
        return "redirect:/admin/orders";
    }
}