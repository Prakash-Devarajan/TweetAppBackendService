package com.tweetapp.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.constant.Constants;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.kafka.ConsumerService;
import com.tweetapp.kafka.ProducerService;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

@Service

public class TweetServiceImpl implements TweetService {

	@Autowired
	TweetRepository tweetRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ProducerService producerService;
	@Autowired
	ConsumerService consumerService;
	
	private static final Logger log = LogManager.getLogger(TweetServiceImpl.class);

	@Override
	public List<Tweet> getAllTweets() {
		producerService.sendMessage("Entering getAllTweets() method");
		log.info("List of Tweets retrieved successfully");
		producerService.sendMessage("Exiting getAllTweets() method");
		return tweetRepo.findAll();
	}

	@Override
	public List<Tweet> getTweetsByUser(String username) {
		producerService.sendMessage("Entering getTweetsByUser() method");
		log.info("Got Tweets by getTweetsByUser method");
		producerService.sendMessage("Exiting getTweetsByUser() method");
		return tweetRepo.findByUserLoginId(username);
	}

	@Override
	public Tweet postTweet(String username, Tweet tweet) {
		producerService.sendMessage("Entering postTweet() method");
		User user = userRepo.findByLoginId(username);
		tweet.setUser(user);
		log.info("Tweet posted Successfully");
		producerService.sendMessage("Exiting postTweet() method");
		return tweetRepo.save(tweet);

	}

	@Override
	public Tweet updateTweet(String username, String id, Tweet tweet) {
		producerService.sendMessage("Entering updateTweet() method");
		User user = userRepo.findByLoginId(username);
		tweet.setUser(user);
		log.info("Tweet Updated Successfully");
		producerService.sendMessage("Exiting updateTweet() method");
		return tweetRepo.save(tweet);

	}

	@Override
	public void deleteTweet(String username, String id) {
		producerService.sendMessage("Entering deleteTweet() method");
		log.info("Tweet deleted Successfully");
		producerService.sendMessage("Exiting deleteTweet() method");
		tweetRepo.deleteById(id);
	}

	@Override
	public void addLike(String username, String id) {
		producerService.sendMessage("Entering addLike() method");
		Optional<Tweet> tweet = tweetRepo.findById(id);
		if (tweet.isPresent()) {
			tweet.get().setLikes(tweet.get().getLikes() + 1);
			tweetRepo.save(tweet.get());
			log.info("Added Like to Tweet Successfully");
		}
		producerService.sendMessage("Exiting addLike() method");
	}

	@Override
	public Tweet replyTweet(String username, String id, Tweet reply) throws TweetNotFoundException {
		
		producerService.sendMessage("Entering replyTweet() method");
		Optional<Tweet> originalTweet = tweetRepo.findById(id);

		if (originalTweet.isPresent()) {
			User user = userRepo.findByLoginId(username);
			reply.setUser(user);
			List<Tweet> replies = originalTweet.get().getReplies();
			replies.add(reply);
			tweetRepo.save(originalTweet.get());
			log.info("Tweet Replied Successfully");
		} else {
			log.error(Constants.TWEET_NOT_FOUND);
			throw new TweetNotFoundException(Constants.TWEET_NOT_FOUND);
		}
		producerService.sendMessage("Exiting replyTweet() method");
		return originalTweet.get();

	}

	@Override
	public Tweet postTweetKafka(String username, Tweet tweet) {
		producerService.sendMessage("Entering postTweetKafka() method");
		User user = userRepo.findByLoginId(username);
		tweet.setUser(user);
		producerService.postTweet(tweet);
		log.info("Tweet posted successsfully through Kafka");
		producerService.sendMessage("Exiting postTweetKafka() method");
		return consumerService.consumeTweet(tweet);
	}

}
