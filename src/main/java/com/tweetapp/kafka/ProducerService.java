package com.tweetapp.kafka;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tweetapp.model.Tweet;

@Service

public class ProducerService {

	private static final Logger log = LogManager.getLogger(ProducerService.class);

	// General topic with a string payload

	public static final String topicName = "Logs";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	// Topic with Tweet object payload

	public static final String TweetTopicName = "TweetApp";

	@Autowired
	private KafkaTemplate<String, Tweet> TweetKafkaTemplate;

	public void sendMessage(String message) {
		kafkaTemplate.send(topicName, message);
		log.info("Message posted successfully into Logs kafka topic");

	}

	public void postTweet(Tweet tweet) {
		log.info("Tweet posted successfully through TweetApp kafka topic");
		TweetKafkaTemplate.send(TweetTopicName, tweet);

	}
}
