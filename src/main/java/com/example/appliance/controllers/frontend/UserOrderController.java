package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Customer;
import com.example.appliance.models.OrderInfo;
import com.example.appliance.services.CurrentUserService;
import com.example.appliance.services.OrderInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/my-orders")
@PreAuthorize("hasRole('USER')")
public class UserOrderController {

    private final CurrentUserService currentUserService;
    private final OrderInfoService orderInfoService;

    public UserOrderController(CurrentUserService currentUserService,
                               OrderInfoService orderInfoService) {
        this.currentUserService = currentUserService;
        this.orderInfoService = orderInfoService;
    }

    @GetMapping
    public String listMyOrders(Model model) {
        Customer currentCustomer = currentUserService.getCurrentCustomer();
        model.addAttribute("orders", orderInfoService.findByCustomerId(currentCustomer.getIdCustomer()));
        return "user/orders/list";
    }

    @GetMapping("/{id}")
    public String viewMyOrder(@PathVariable Integer id, Model model) {
        Customer currentCustomer = currentUserService.getCurrentCustomer();
        OrderInfo order = orderInfoService.findById(id);

        if (!order.getCustomer().getIdCustomer().equals(currentCustomer.getIdCustomer())) {
            throw new RuntimeException("Нет доступа к этому заказу");
        }

        model.addAttribute("order", order);
        return "user/orders/view";
    }
}