package com.app.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.RecordNotFoundException;
import com.app.model.User;
import com.app.serviceImpl.UserServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	Logger logger = LogManager.getLogger("UserController.class");

	@Autowired
	private UserServiceImpl service;

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = service.getAllUsers();
		if (users.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		logger.info("Users List Request Completed");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		try {
			logger.info("User Request Completed");
			return new ResponseEntity<>(service.getUserById(id), HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
		logger.info("Add User Request Completed");
		return new ResponseEntity<>(service.addUser(user), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable int id) {
		try {
			logger.info("Update User Request Completed");
			return new ResponseEntity<>(service.updateUser(user, id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		try {
			logger.info("Delete User Request Completed");
			service.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}

	}
}
