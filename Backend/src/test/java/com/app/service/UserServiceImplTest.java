package com.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.enums.Gender;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserServiceImpl service;

	@Test
	public void testGetAllUsers() {
		List<User> allUsers = new ArrayList<>();
		allUsers.add(new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M));
		Mockito.when(repository.findAll()).thenReturn(allUsers);
		List<User> fetchedUsers = service.getAllUsers();
		assertThat(fetchedUsers.size()).isGreaterThan(0);
	}

	@Test
	public void testGetUserById() {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(repository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
		assertThat(service.getUserById(1)).isNotNull();
	}

	@Test
	public void testAddUser() {
		User testUser = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(repository.save(testUser)).thenReturn(testUser);
		assertThat(repository.save(testUser).getName()).isNotNull();
	}

	@Test
	public void testUpdateUser() {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(repository.save(testUser)).thenReturn(testUser);
		assertThat(repository.save(testUser).getName()).isNotNull();
	}

	@Test
	public void testDeleteUser() {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		repository.deleteById(testUser.getId());
		verify(repository, times(1)).deleteById(testUser.getId());
	}

}
