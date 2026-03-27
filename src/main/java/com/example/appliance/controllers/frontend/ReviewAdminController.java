package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.appliance.models.Review;
import com.example.appliance.services.CustomerService;
import com.example.appliance.services.ProductService;
import com.example.appliance.services.ReviewService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/reviews")
public class ReviewAdminController {

    private final ReviewService reviewService;
    private final CustomerService customerService;
    private final ProductService productService;

    public ReviewAdminController(ReviewService reviewService,
                                 CustomerService customerService,
                                 ProductService productService) {
        this.reviewService = reviewService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @GetMapping
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "admin/reviews/list";
    }

    @GetMapping("/{id}")
    public String viewReview(@PathVariable Integer id, Model model) {
        model.addAttribute("review", reviewService.findById(id));
        return "admin/reviews/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("formTitle", "Добавление отзыва");
        return "admin/reviews/form";
    }

    @PostMapping
    public String createReview(@Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               @RequestParam("customerId") Integer customerId,
                               @RequestParam("productId") Integer productId,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("formTitle", "Добавление отзыва");
            return "admin/reviews/form";
        }

        review.setCustomer(customerService.findById(customerId));
        review.setProduct(productService.findById(productId));
        reviewService.save(review);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("review", reviewService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("formTitle", "Редактирование отзыва");
        return "admin/reviews/form";
    }

    @PostMapping("/{id}")
    public String updateReview(@PathVariable Integer id,
                               @Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               @RequestParam("customerId") Integer customerId,
                               @RequestParam("productId") Integer productId,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("formTitle", "Редактирование отзыва");
            return "admin/reviews/form";
        }

        review.setIdReview(id);
        review.setCustomer(customerService.findById(customerId));
        review.setProduct(productService.findById(productId));
        reviewService.save(review);
        return "redirect:/admin/reviews";
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Integer id) {
        reviewService.deleteById(id);
        return "redirect:/admin/reviews";
    }
}