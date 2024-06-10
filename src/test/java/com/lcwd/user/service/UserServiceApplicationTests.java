package com.lcwd.user.service;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;

	  @Test
    void createRating() {
       // Rating rating = Rating.builder().rating(10).userId("").hotelId("").feedback("this is created using feign client").build();
		  Rating rating = new Rating();
		  rating.setRating(10);
		  rating.setUserId("");
		  rating.setHotelId("");
		  rating.setFeedback("this is created using feign client");

			//Rating savedRating=ratingService.createRating(rating);
		 ResponseEntity<Rating> ratingResponseEntity = ratingService.createRating(rating);
        System.out.println("new rating created");
    }

}
