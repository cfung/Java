package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
