package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entities.Comment;
import com.udacity.course3.reviews.entities.Product;
import com.udacity.course3.reviews.entities.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {



	@Autowired private DataSource dataSource;
	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private EntityManager entityManager;
	@Autowired private TestEntityManager testEntityManager;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private CommentRepository commentRepository;


	@Test
	public void contextLoads() {

		assertThat(dataSource).isNotNull();
		assertThat(jdbcTemplate).isNotNull();
		assertThat(entityManager).isNotNull();
		assertThat(testEntityManager).isNotNull();
		assertThat(reviewRepository).isNotNull();
		assertThat(productRepository).isNotNull();
		assertThat(commentRepository).isNotNull();
	}

	// test for ReviewRepository
	@Test
	public void testReviewRepositoryFindAllByProduct() {

		Product product = new Product();
		product.setName("product1");
		product.setDescription("mydescription");
		entityManager.persist(product);

		List<Review> reviews = reviewRepository.findAllByProduct(product); //TODO:  change to find by Name
		assertThat(reviews).isNotNull();

	}

	@Test
	public void testCommentRepositoryFindAllByReview() {

		Product product = new Product();
		product.setName("product2");
		product.setDescription("prod description");

		Review review = new Review();
		review.setTitle("myReivewTitle");
		review.setRecommended(true);
		review.setReviewText("highly recommended");
		review.setProduct(product);

		Comment comment = new Comment();
		comment.setTitle("comment");
		comment.setCommentText("comment text");
		comment.setReview(review);

		entityManager.persist(product);
		entityManager.persist(review);

		List<Comment> comments = commentRepository.findAllByReview(review);

		assertThat(comments).isNotNull();

	}
}