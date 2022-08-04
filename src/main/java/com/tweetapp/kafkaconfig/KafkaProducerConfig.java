package com.tweetapp.kafkaconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tweetapp.model.Tweet;

@Configuration
@EnableKafka

public class KafkaProducerConfig {

	private static final Logger log = LogManager.getLogger(KafkaProducerConfig.class);
	
	private String bootstrapAddress = "localhost:9092";

	// 1. Send string to Kafka

	@Bean
	public ProducerFactory<String, String> producerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		log.info("Kalfa configuration for string producer method is completed");
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		log.info("Kafka producer bean for string method created");
		return new KafkaTemplate<>(producerFactory());
	}

	// 2. Send User objects to Kafka
	@Bean
	public ProducerFactory<String, Tweet> TweetProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		log.info("Kalfa configuration for tweet producer method is completed");
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Tweet> userKafkaTemplate() {
		log.info("kafka producer bean for tweet method created");
		return new KafkaTemplate<>(TweetProducerFactory());
	}
}