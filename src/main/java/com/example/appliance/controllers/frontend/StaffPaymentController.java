package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Payment;
import com.example.appliance.services.OrderInfoService;
import com.example.appliance.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/payments")
public class StaffPaymentController {

    private final PaymentService paymentService;
    private final OrderInfoService orderInfoService;

    public StaffPaymentController(PaymentService paymentService,
                                  OrderInfoService orderInfoService) {
        this.paymentService = paymentService;
        this.orderInfoService = orderInfoService;
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.findAll());
        return "staff/payments/list";
    }

    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Integer id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));
        return "staff/payments/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("orders", orderInfoService.findAll());
        model.addAttribute("formTitle", "Добавление оплаты");
        return "staff/payments/form";
    }

    @PostMapping
    public String createPayment(@Valid @ModelAttribute("payment") Payment payment,
                                BindingResult bindingResult,
                                @RequestParam("orderId") Integer orderId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderInfoService.findAll());
            model.addAttribute("formTitle", "Добавление оплаты");
            return "staff/payments/form";
        }

        payment.setOrderInfo(orderInfoService.findById(orderId));
        paymentService.save(payment);
        return "redirect:/staff/payments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));
        model.addAttribute("orders", orderInfoService.findAll());
        model.addAttribute("formTitle", "Редактирование оплаты");
        return "staff/payments/form";
    }

    @PostMapping("/{id}")
    public String updatePayment(@PathVariable Integer id,
                                @Valid @ModelAttribute("payment") Payment payment,
                                BindingResult bindingResult,
                                @RequestParam("orderId") Integer orderId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderInfoService.findAll());
            model.addAttribute("formTitle", "Редактирование оплаты");
            return "staff/payments/form";
        }

        payment.setIdPayment(id);
        payment.setOrderInfo(orderInfoService.findById(orderId));
        paymentService.save(payment);
        return "redirect:/staff/payments";
    }

    @PostMapping("/{id}/delete")
    public String deletePayment(@PathVariable Integer id) {
        paymentService.deleteById(id);
        return "redirect:/staff/payments";
    }
}