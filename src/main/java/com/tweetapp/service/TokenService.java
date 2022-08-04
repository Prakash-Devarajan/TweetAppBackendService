package com.tweetapp.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
	
	private static final Logger log = LogManager.getLogger(TokenService.class);

	public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";

	public String createToken(ObjectId userId) throws UnsupportedEncodingException {
		try {
			log.info("Entering createToken() method");
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token = JWT.create().withClaim("userId", userId.toString()).withClaim("createdAt", new Date())
					.sign(algorithm);
			log.info("Token generated Successfully");
			log.info("Exiting createToken() method");
			return token;
		} catch (JWTCreationException exception) {
			log.error(exception.getMessage());
		}
		return null;
	}

	public String getUserIdFromToken(String token) throws UnsupportedEncodingException {
		try {
			log.info("Entering getUserIdFromToken() method");
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			log.info("Exiting getUserIdFromToken() method");
			log.info("Assigned user with the Token successfully");
			return jwt.getClaim("userId").asString();
		} catch (Exception exception) {
			log.error(exception.getMessage());
			return null;
		}
	}

	public boolean isTokenValid(String token) throws UnsupportedEncodingException {
		log.info("Entering isTokenValid() method");
		String userId = this.getUserIdFromToken(token);
		log.info("Token Validated Successfully");
		log.info("Exiting isTokenValid() method");
		return userId != null;
	}
}