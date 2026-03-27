package com.example.appliance.controllers.frontend;

import com.example.appliance.models.OrderItem;
import com.example.appliance.services.OrderInfoService;
import com.example.appliance.services.OrderItemService;
import com.example.appliance.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/order-items")
public class StaffOrderItemController {

    private final OrderItemService orderItemService;
    private final OrderInfoService orderInfoService;
    private final ProductService productService;

    public StaffOrderItemController(OrderItemService orderItemService,
                                    OrderInfoService orderInfoService,
                                    ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderInfoService = orderInfoService;
        this.productService = productService;
    }

    @GetMapping
    public String listOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.findAll());
        return "staff/order-items/list";
    }

    @GetMapping("/{id}")
    public String viewOrderItem(@PathVariable Integer id, Model model) {
        model.addAttribute("orderItem", orderItemService.findById(id));
        return "staff/order-items/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("orders", orderInfoService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("formTitle", "Добавление позиции заказа");
        return "staff/order-items/form";
    }

    @PostMapping
    public String createOrderItem(@Valid @ModelAttribute("orderItem") OrderItem orderItem,
                                  BindingResult bindingResult,
                                  @RequestParam("orderId") Integer orderId,
                                  @RequestParam("productId") Integer productId,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderInfoService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("formTitle", "Добавление позиции заказа");
            return "staff/order-items/form";
        }

        orderItem.setOrderInfo(orderInfoService.findById(orderId));
        orderItem.setProduct(productService.findById(productId));
        orderItemService.save(orderItem);
        return "redirect:/staff/order-items";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("orderItem", orderItemService.findById(id));
        model.addAttribute("orders", orderInfoService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("formTitle", "Редактирование позиции заказа");
        return "staff/order-items/form";
    }

    @PostMapping("/{id}")
    public String updateOrderItem(@PathVariable Integer id,
                                  @Valid @ModelAttribute("orderItem") OrderItem orderItem,
                                  BindingResult bindingResult,
                                  @RequestParam("orderId") Integer orderId,
                                  @RequestParam("productId") Integer productId,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderInfoService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("formTitle", "Редактирование позиции заказа");
            return "staff/order-items/form";
        }

        orderItem.setIdOrderItem(id);
        orderItem.setOrderInfo(orderInfoService.findById(orderId));
        orderItem.setProduct(productService.findById(productId));
        orderItemService.save(orderItem);
        return "redirect:/staff/order-items";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrderItem(@PathVariable Integer id) {
        orderItemService.deleteById(id);
        return "redirect:/staff/order-items";
    }
}