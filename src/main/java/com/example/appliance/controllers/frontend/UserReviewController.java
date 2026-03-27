package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Customer;
import com.example.appliance.models.Product;
import com.example.appliance.models.Review;
import com.example.appliance.services.CurrentUserService;
import com.example.appliance.services.ProductService;
import com.example.appliance.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/my-reviews")
@PreAuthorize("hasRole('USER')")
public class UserReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final CurrentUserService currentUserService;

    public UserReviewController(ReviewService reviewService,
                                ProductService productService,
                                CurrentUserService currentUserService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public String listMyReviews(Model model) {
        Customer currentCustomer = currentUserService.getCurrentCustomer();
        model.addAttribute("reviews", reviewService.findByCustomerId(currentCustomer.getIdCustomer()));
        return "user/reviews/list";
    }

    @GetMapping("/new/{productId}")
    public String createForm(@PathVariable Integer productId, Model model) {
        Product product = productService.findById(productId);
        Review review = new Review();
        review.setProduct(product);

        model.addAttribute("review", review);
        model.addAttribute("product", product);
        return "user/reviews/form";
    }

    @PostMapping("/new/{productId}")
    public String createReview(@PathVariable Integer productId,
                               @Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               Model model) {
        Product product = productService.findById(productId);
        Customer currentCustomer = currentUserService.getCurrentCustomer();

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "user/reviews/form";
        }

        review.setCustomer(currentCustomer);
        review.setProduct(product);
        reviewService.save(review);

        return "redirect:/my-reviews";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Review review = reviewService.findById(id);
        Customer currentCustomer = currentUserService.getCurrentCustomer();

        if (!review.getCustomer().getIdCustomer().equals(currentCustomer.getIdCustomer())) {
            throw new RuntimeException("Нет доступа к отзыву");
        }

        model.addAttribute("review", review);
        model.addAttribute("product", review.getProduct());
        return "user/reviews/form";
    }

    @PostMapping("/{id}/edit")
    public String updateReview(@PathVariable Integer id,
                               @Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               Model model) {
        Review existingReview = reviewService.findById(id);
        Customer currentCustomer = currentUserService.getCurrentCustomer();

        if (!existingReview.getCustomer().getIdCustomer().equals(currentCustomer.getIdCustomer())) {
            throw new RuntimeException("Нет доступа к отзыву");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", existingReview.getProduct());
            return "user/reviews/form";
        }

        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setReviewDate(review.getReviewDate());

        reviewService.save(existingReview);
        return "redirect:/my-reviews";
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Integer id) {
        Review review = reviewService.findById(id);
        Customer currentCustomer = currentUserService.getCurrentCustomer();

        if (!review.getCustomer().getIdCustomer().equals(currentCustomer.getIdCustomer())) {
            throw new RuntimeException("Нет доступа к отзыву");
        }

        reviewService.deleteById(id);
        return "redirect:/my-reviews";
    }
}