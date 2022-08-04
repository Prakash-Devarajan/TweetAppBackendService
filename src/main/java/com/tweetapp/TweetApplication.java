package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoAuditing
@EnableSwagger2
@Slf4j
public class TweetApplication {

	public static void main(String[] args) {

		SpringApplication.run(TweetApplication.class, args);
	}

}
