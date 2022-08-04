package com.tweetapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.constant.Constants;
import com.tweetapp.exception.InvalidCredentialsException;
import com.tweetapp.exception.PasswordMismatchException;
import com.tweetapp.exception.UserAlreadyExist;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.kafka.ProducerService;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.User;
import com.tweetapp.model.UserResponse;
import com.tweetapp.service.UserService;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private ProducerService producerService;
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@PostMapping("/register") // http://localhost:8080/api/v1.0/tweets/register
	ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExist {
		try {
			producerService.sendMessage("Started regsiterUser() method");
			log.info("New User Creation started in controller");
			producerService.sendMessage("Exiting regsiterUser() method");
			return new ResponseEntity<>(service.newUser(user), HttpStatus.CREATED);
		} catch (UserAlreadyExist e) {
			log.error(e.getMessage());
			throw new UserAlreadyExist(Constants.USER_ALREADY_EXIST);
		}

	}

	@PostMapping("/login") // http://localhost:8080/api/v1.0/tweets/login
	ResponseEntity<UserResponse> loginUser(@RequestBody User user, HttpServletRequest request)
			throws InvalidCredentialsException, UnsupportedEncodingException {
		try {
			UserResponse status = service.loginUser(user.getLoginId(), user.getPassword());
			if (status != null) {
				request.getSession().setAttribute("user", user.getLoginId());
				producerService.sendMessage("Started loginUser() method");
				log.info("User Login started in controller");
				producerService.sendMessage("Exciting loginUser() method");
				return new ResponseEntity<>(status, HttpStatus.OK);
			} else {
				throw new InvalidCredentialsException(Constants.INVALID_CREDENTIALS);
			}
		} catch (InvalidCredentialsException e) {
			log.error(e.getMessage());
			throw new InvalidCredentialsException(Constants.INVALID_CREDENTIALS);

		}

	}

	@PostMapping("/{username}/forgot") // http://localhost:8080/api/v1.0/tweets/prakash/forgot
	ResponseEntity<String> forgotPassword(@PathVariable String username, @RequestBody ForgotPassword forgotPassword)
			throws PasswordMismatchException, UserNotFoundException {
		producerService.sendMessage("Started forgotPassword() method");
		log.info("Password reset started in controller");
		producerService.sendMessage("Exiting forgotPassword() method");
		return new ResponseEntity<>(service.forgotPassword(username, forgotPassword), HttpStatus.OK);
	}

	@GetMapping("/users/all")
	ResponseEntity<List<User>> getAllUsers()// http://localhost:8080/api/v1.0/tweets/users/all
	{

		List<User> users = service.getAllUsers();
		producerService.sendMessage("Started getAllUsers() method");
		log.info("Fecting All Users List started in controller ");
		producerService.sendMessage("Exiting getAllUsers() method");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/user/search/{username}") // http://localhost:8080/api/v1.0/tweets/user/search/madhu
	ResponseEntity<List<User>> getUserByName(@PathVariable String username) throws UserNotFoundException {
		List<User> users = service.getUserByName(username);
		producerService.sendMessage("Started getUserByName() method");
		log.info("Fetching Users By Name started in controller");
		producerService.sendMessage("Exiting getUserByName() method");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
