package com.example.appliance.controllers.frontend;

import com.example.appliance.services.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("cartItems", cartService.getCurrentCartItems());
        model.addAttribute("total", cartService.getTotal());
        return "user/cart/view";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Integer productId,
                            @RequestParam(defaultValue = "1") Integer quantity) {
        cartService.addProduct(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update/{cartItemId}")
    public String updateCartItem(@PathVariable Integer cartItemId,
                                 @RequestParam Integer quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable Integer cartItemId) {
        cartService.removeItem(cartItemId);
        return "redirect:/cart";
    }
}