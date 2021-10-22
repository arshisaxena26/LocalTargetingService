package com.app.service;

import java.util.List;

import com.app.model.User;

public interface IUserService {

	public List<User> getAllUsers();

	public User getUserById(int id);

	public User addUser(User user);

	public User updateUser(User user, int id);

	public void deleteUser(int id);

}
