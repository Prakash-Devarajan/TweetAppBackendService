package com.tweetapp.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tweetapp.controller.TweetController;
import com.tweetapp.model.Tweet;
import com.tweetapp.repository.TweetRepository;

@Service

public class ConsumerService {
	@Autowired
	TweetRepository tweetRepository;
	
	private static final Logger log = LogManager.getLogger(ConsumerService.class);

	@KafkaListener(topics = "Logs", groupId = "group_id1")
	public void consume(String message) {
		log.info(message);

	}

	@KafkaListener(topics = "TweetApp", groupId = "group_id2", containerFactory = "userKafkaListenerContainerFactory")
	public Tweet consumeTweet(Tweet tweet) {
		tweetRepository.save(tweet);
		log.info("Consumed message " + tweet.toString());
		return tweet;

	}
}
