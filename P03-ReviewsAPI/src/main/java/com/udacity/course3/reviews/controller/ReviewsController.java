package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entities.Product;
import com.udacity.course3.reviews.entities.Review;
import com.udacity.course3.reviews.entities.mongo.ReviewMongo;
import com.udacity.course3.reviews.mongo_repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ReviewMongoRepository reviewMongoRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository, ReviewMongoRepository reviewMongoRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
       // this.reviewMongoRepository = reviewMongoRepository;
        this.reviewMongoRepository = reviewMongoRepository;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> optional = productRepository.findById(productId);

        if (optional.isPresent()) {

            review.setProduct(optional.get());

            // save data in MongoDB
            ReviewMongo reviewMongo = new ReviewMongo();
            reviewMongoRepository.save(reviewMongo);

            return ResponseEntity.ok(reviewRepository.save(review));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        List<Integer> reviewIds = reviewRepository.findReviewIdsByProductId(productId);
        List<String> stringIdsList = new ArrayList<>(reviewIds.size());
        reviewIds.forEach(id -> stringIdsList.add(id.toString()));

        List<ReviewMongo> reviews = StreamSupport.stream(
                reviewMongoRepository.findAllById(stringIdsList).spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }
}