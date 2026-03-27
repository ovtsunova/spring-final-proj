package com.example.appliance.repositories;

import com.example.appliance.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_IdCart(Integer cartId);
    Optional<CartItem> findByCart_IdCartAndProduct_IdProduct(Integer cartId, Integer productId);
}