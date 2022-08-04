package com.tweetapp.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.constant.Constants;
import com.tweetapp.exception.InvalidCredentialsException;
import com.tweetapp.exception.PasswordMismatchException;
import com.tweetapp.exception.UserAlreadyExist;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.kafka.ProducerService;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.User;
import com.tweetapp.model.UserResponse;
import com.tweetapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ProducerService producerService;

	public String registerUser(User user) {
		producerService.sendMessage("Entering createToken() method");
		repo.save(user);
		log.info("New User created successfully");
		producerService.sendMessage("Exiting isTokenValid() method");
		return "User Added Successfully";

	}

	@Override
	public List<User> getAllUsers() {
		producerService.sendMessage("Entering getAllUsers() method");
		log.info("List of All users : "+repo.findAll());
		producerService.sendMessage("Exiting getAllUsers() method");
		return repo.findAll();
	}

	@Override
	public List<User> getUserByName(String username) throws UserNotFoundException {
		producerService.sendMessage("Entering getAllUsers() method");
		if (repo.findByLoginIdContaining(username) == null) {
			log.error(Constants.USER_NOT_FOUND);
			throw new UserNotFoundException(Constants.USER_NOT_FOUND);
		}
		log.info("Got User name by name successfully");
		producerService.sendMessage("Exiting getAllUsers() method");
		return repo.findByLoginIdContaining(username);
	}

	@Override
	public User newUser(User user) throws UserAlreadyExist {
		producerService.sendMessage("Entering newUser() method");
		if (repo.findByLoginId(user.getLoginId()) != null) {
			log.error(Constants.USER_ALREADY_EXIST);
			throw new UserAlreadyExist(Constants.USER_ALREADY_EXIST);
		}
		producerService.sendMessage("Exiting newUSer() method");
		log.info("New User Created successfully");
		return repo.save(user);

	}

	@Override
	public UserResponse loginUser(String loginId, String password) throws UnsupportedEncodingException {
		producerService.sendMessage("Entering loginUser() method");
		UserResponse userResponse = new UserResponse();
		try {
			User user = repo.findByLoginId(loginId);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					userResponse.setUser(user);
					userResponse.setLoginStatus(Constants.SUCCESS);
					userResponse.setErrorMessage(null);
					userResponse.setToken(tokenService.createToken(user.getId()));
					log.info("Login Completed Successfully");
				} else {
					log.error(Constants.INCORRECT_PASSWORD);
					throw new InvalidCredentialsException(Constants.INCORRECT_PASSWORD);

				}
			} else {
				log.error(Constants.INCORRECT_USERNNAME);
				throw new InvalidCredentialsException(Constants.INCORRECT_USERNNAME);
			}
		} catch (InvalidCredentialsException e) {
			userResponse.setErrorMessage(Constants.INVALID_CREDENTIALS);
			userResponse.setLoginStatus(Constants.FAIL);
			log.error(e.getMessage());

		}
		producerService.sendMessage("Exiting loginUser() method");
		return userResponse;
	}

	@Override
	public String forgotPassword(String username, ForgotPassword forgotPassword)
			throws PasswordMismatchException, UserNotFoundException {
		producerService.sendMessage("Entering forgotPassword() method");
		try {
			User user = repo.findByLoginId(username);
			if (user != null) {
				if (forgotPassword.getPassword().equals(forgotPassword.getConfirmPassword())) {
					user.setPassword(forgotPassword.getPassword());
					user.setConfirmPasword(forgotPassword.getConfirmPassword());
					repo.save(user);
					log.info("Password reset successfully");
					producerService.sendMessage("Exiting forgotPassword() method");
					return Constants.PASSWORD_CHANGED;
				} else {
					log.error(Constants.PASSWORD_MISMATCH);
					throw new PasswordMismatchException(Constants.PASSWORD_MISMATCH);
				}
			} else {
				log.error(Constants.USER_NOT_FOUND);
				throw new UserNotFoundException(Constants.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return e.getMessage();

		}
	}

}
