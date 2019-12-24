package com.udacity.course3.reviews.mongo_repository;

import com.udacity.course3.reviews.entities.Product;
import com.udacity.course3.reviews.entities.Review;
import com.udacity.course3.reviews.entities.mongo.ReviewMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewMongoRepository extends MongoRepository<ReviewMongo, String> {

}
