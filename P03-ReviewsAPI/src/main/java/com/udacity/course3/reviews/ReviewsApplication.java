package com.udacity.course3.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
// step 1:
@EnableMongoRepositories
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}


// step 2:
// @Document("patients")
// public class Patient {

// step 3:
// @Repository
// public interface PatientRepository extends MongoRepository <Patient, String> {
// TODO: do I change the existing extend to JpaRepository or create new Repository class?