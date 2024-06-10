package com.lcwd.user.service.controller;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lcwd.user.service.entities.User.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get


    @GetMapping("/{userId}")
   // @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
  // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        logger.info("Get Single User Handler: UserController");
//        logger.info("Retry count: {}", retryCount);

        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back  method for circuitbreaker


    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
//        logger.info("Fallback is executed because service is down : ", ex.getMessage());

        ex.printStackTrace();

        User user = new User();
        user.setEmail("dummy@gmail.com");
        user.setName("Dummy");
        user.setAbout("This user is created dummy because some service is down");
        user.setUserId("141234");

        System.out.println(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getAbout());
        System.out.println(user.getUserId());

        //User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created dummy because some service is down").userId("141234").build();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }


    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}