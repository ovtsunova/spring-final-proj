package com.example.appliance.services;

import com.example.appliance.models.Review;
import com.example.appliance.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв не найден."));
    }

    public List<Review> findByCustomerId(Integer customerId) {
        return reviewRepository.findByCustomer_IdCustomer(customerId);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }
}