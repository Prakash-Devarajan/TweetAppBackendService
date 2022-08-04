package com.tweetapp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.kafka.ProducerService;
import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetService;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

	@Autowired
	TweetService tweetService;
	@Autowired
	ProducerService producerService;
	
	private static final Logger log = LogManager.getLogger(TweetController.class);

	@GetMapping("/all") // http://localhost:8080/api/v1.0/tweets/all
	public ResponseEntity<List<Tweet>> getAllTweets() {
		producerService.sendMessage("Started getAllTweets() method");
		List<Tweet> tweets = tweetService.getAllTweets();
		log.info("Fetching List of all Tweets started in controller");
		producerService.sendMessage("Exiting getAllTweets() method");
		return new ResponseEntity<>(tweets, HttpStatus.OK);
	}

	@GetMapping("/{username}") // http://localhost:8080/api/v1.0/tweets/prakash
	public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable String username) {
		producerService.sendMessage("Started getTweetsByUser() method");
		List<Tweet> userTweets = tweetService.getTweetsByUser(username);
		log.info("Fetching Tweet by username started in controller");
		producerService.sendMessage("Exiting getTweetsByUser() method");
		return new ResponseEntity<>(userTweets, HttpStatus.OK);
	}

	@PostMapping("/{username}/add") // http://localhost:8080/api/v1.0/tweets/prakash/add
	public ResponseEntity<Tweet> postTweet(@PathVariable String username, @RequestBody Tweet tweet) {
		producerService.sendMessage("Started postTweet() method");
		log.info("Posting tweet started in controller");
		producerService.sendMessage("Started postTweet() method");
		return new ResponseEntity<>(tweetService.postTweet(username, tweet), HttpStatus.OK);
	}

	// KAFKA IMPLEMENTATION

	@PostMapping("/{username}/addkafka") // http://localhost:8080/api/v1.0/tweets/prakash/addkafka
	public ResponseEntity<Tweet> postTweetKafka(@PathVariable String username, @RequestBody Tweet tweet) {
		producerService.sendMessage("Started postTweetKafka() method");
		log.info("Posted tweet through Kafka started in controller");
		producerService.sendMessage("Exiting postTweetKafka() method");
		return new ResponseEntity<>(tweetService.postTweetKafka(username, tweet), HttpStatus.OK);
	}

	@PutMapping("/{username}/update/{id}") // http://localhost:8080/api/v1.0/tweets/prakash/update/1
	public ResponseEntity<Tweet> updateTweet(@PathVariable String username, @PathVariable String id,
			@RequestBody Tweet tweet) {
		producerService.sendMessage("Started updateTweet() method");
		log.info("Updating Tweet started in controller");
		producerService.sendMessage("Exiting updateTweet() method");
		return new ResponseEntity<>(tweetService.updateTweet(username, id, tweet), HttpStatus.OK);
	}

	@DeleteMapping("/{username}/delete/{id}") // http://localhost:8080/api/v1.0/tweets/prakash/delete/2
	public ResponseEntity<HttpStatus> deleteTweet(@PathVariable String username, @PathVariable String id) {
		producerService.sendMessage("Started deleteTweet() method");
		log.info("Deleting Tweet started in controller");
		tweetService.deleteTweet(username, id);
		producerService.sendMessage("Exiting deleteTweet() method");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{username}/like/{id}") // http://localhost:8080/api/v1.0/tweets/prakash/like/0
	public ResponseEntity<HttpStatus> dropLike(@PathVariable String username, @PathVariable String id) {
		producerService.sendMessage("Started dropLike() method");
		log.info("Adding Like to tweet started in controller");
		tweetService.addLike(username, id);
		producerService.sendMessage("Exiting dropLike() method");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{username}/reply/{id}") //// http://localhost:8080/api/v1.0/tweets/prakash/reply/0
	public ResponseEntity<Tweet> replyTweet(@PathVariable String username, @PathVariable String id,
			@RequestBody Tweet reply) throws TweetNotFoundException {
		producerService.sendMessage("Started replyTweet() method");
		log.info("Repling Tweet started in controller");
		producerService.sendMessage("Exiting replyTweet() method");
		return new ResponseEntity<>(tweetService.replyTweet(username, id, reply), HttpStatus.OK);
	}

}
