package com.example.appliance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appliance.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_IdProduct(Integer idProduct);
    List<Review> findByCustomer_IdCustomer(Integer idCustomer);
    List<Review> findByRating(Integer rating);
}