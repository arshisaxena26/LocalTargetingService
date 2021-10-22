package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.User;
import com.app.repository.TargetedOfferRepository;
import com.app.repository.UserRepository;
import com.app.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TargetedOfferRepository offerRepo;

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepo.findAll()
				.forEach(u -> users.add(new User(u.getId(), u.getName(), u.getEmail(), u.getDob(), u.getGender())));
		return users;
	}

	@Override
	public User getUserById(int id) {
		User user = userRepo.findById(id).get();
		return new User(user.getId(), user.getName(), user.getEmail(), user.getDob(), user.getGender());
	}

	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user, int id) {
		User updatedUser = getUserById(id);
		updatedUser.setDob(user.getDob());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setGender(user.getGender());
		updatedUser.setName(user.getName());
		return userRepo.save(updatedUser);
	}

	@Override
	public void deleteUser(int id) {
		User user = getUserById(id);
		offerRepo.deleteByUser(user);
		userRepo.delete(user);
	}
}
