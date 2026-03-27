package com.example.appliance.controllers.frontend;

import com.example.appliance.models.OrderInfo;
import com.example.appliance.services.CustomerService;
import com.example.appliance.services.OrderInfoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/orders")
public class StaffOrderInfoController {

    private final OrderInfoService orderInfoService;
    private final CustomerService customerService;

    public StaffOrderInfoController(OrderInfoService orderInfoService,
                                    CustomerService customerService) {
        this.orderInfoService = orderInfoService;
        this.customerService = customerService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderInfoService.findAll());
        return "staff/orders/list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderInfoService.findById(id));
        return "staff/orders/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("order", new OrderInfo());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("formTitle", "Добавление заказа");
        return "staff/orders/form";
    }

    @PostMapping
    public String createOrder(@Valid @ModelAttribute("order") OrderInfo order,
                              BindingResult bindingResult,
                              @RequestParam("customerId") Integer customerId,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("formTitle", "Добавление заказа");
            return "staff/orders/form";
        }

        order.setCustomer(customerService.findById(customerId));
        orderInfoService.save(order);
        return "redirect:/staff/orders";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderInfoService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("formTitle", "Редактирование заказа");
        return "staff/orders/form";
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
            return "staff/orders/form";
        }

        order.setIdOrder(id);
        order.setCustomer(customerService.findById(customerId));
        orderInfoService.save(order);
        return "redirect:/staff/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Integer id) {
        orderInfoService.deleteById(id);
        return "redirect:/staff/orders";
    }
}