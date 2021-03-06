package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entities.Comment;
import com.udacity.course3.reviews.entities.Product;
import com.udacity.course3.reviews.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByProduct(Product product);

    @Query("Select r.id from Review r join r.product p where p.id = ?1")
    List<Integer> findReviewIdsByProductId(Integer productId);
}
