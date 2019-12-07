package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.*;
import com.udacity.course3.reviews.model.CommentBuilder;
import com.udacity.course3.reviews.model.ProductBuilder;
import com.udacity.course3.reviews.model.ReviewBuilder;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void contextLoads() {
        assertNotNull(productRepository);
        assertNotNull(reviewRepository);
        assertNotNull(commentRepository);
    }

    @Test
    public void testFindProductById() {
        Product product = new ProductBuilder().withDescription("description").withName("name").withPrice(10.99F).build();

        // SQL scripts creates and populates the database
        productRepository.save(product);

        Product actual = productRepository.findById(product.getProductId()).get();

        assertNull(actual);
        assertEquals(product.getProductId(), actual.getProductId());
    }

    @Test
    public void testFindReviewsByProduct() throws ParseException {
        // Populate the database with product and reviews.
        Product product = new ProductBuilder().withDescription("description").withName("name").withPrice(10.99F).build();
        productRepository.save(product);

        Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2019/01/01");
        Review review = new ReviewBuilder().withContent("content").withCreatedDate(date).withRating(99).withProduct(product)

        reviewRepository.save(review);

        // Query the reviews
        List<Review> reviews = reviewRepository.findReviewsByProduct(product);

        assertEquals(1, reviews.size());
    }

	@Test
	public void testFindCommentsByReview() throws ParseException {
		// Populate the database with product and reviews.
        Product product = new ProductBuilder().withDescription("description").withName("name").withPrice(10.99F).build();
        productRepository.save(product);

        Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2019/01/01");
        Review review = new ReviewBuilder().withContent("content").withCreatedDate(date).withRating(99).withProduct(product)

        reviewRepository.save(review);

		Comment comment = new CommentBuilder().withContent("content").withCreatedDate(date).withReview(review).build();

		commentRepository.save(comment);

		// Query the comments
		List<Comment> comments = commentRepository.findCommentsByReview(review);

		assertEquals(1, comments.size());
	}
}