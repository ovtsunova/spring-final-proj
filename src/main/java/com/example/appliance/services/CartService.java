package com.example.appliance.services;

import com.example.appliance.models.*;
import com.example.appliance.repositories.CartItemRepository;
import com.example.appliance.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CurrentUserService currentUserService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductService productService,
                       CurrentUserService currentUserService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.currentUserService = currentUserService;
    }

    public Cart getOrCreateCurrentCart() {
        Customer customer = currentUserService.getCurrentCustomer();

        return cartRepository.findByCustomer_IdCustomer(customer.getIdCustomer())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    return cartRepository.save(cart);
                });
    }

    public List<CartItem> getCurrentCartItems() {
        Cart cart = getOrCreateCurrentCart();
        return cartItemRepository.findByCart_IdCart(cart.getIdCart());
    }

    public void addProduct(Integer productId, Integer quantity) {
        Cart cart = getOrCreateCurrentCart();
        Product product = productService.findById(productId);

        CartItem cartItem = cartItemRepository
                .findByCart_IdCartAndProduct_IdProduct(cart.getIdCart(), productId)
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
    }

    public void updateQuantity(Integer cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Позиция корзины не найдена"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeItem(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public BigDecimal getTotal() {
        return getCurrentCartItems().stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clearCurrentCart() {
        List<CartItem> items = getCurrentCartItems();
        cartItemRepository.deleteAll(items);
    }
}