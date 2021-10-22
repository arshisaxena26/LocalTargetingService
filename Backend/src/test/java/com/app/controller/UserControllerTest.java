package com.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.enums.Gender;
import com.app.model.User;
import com.app.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testGetUsers() throws Exception {
		List<User> allUsers = new ArrayList<>();
		allUsers.add(new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M));
		Mockito.when(service.getAllUsers()).thenReturn(allUsers);
		mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.equalTo("Alex")));
	}

	@Test
	public void testGetUsersEmptyList() throws Exception {
		List<User> allUsers = new ArrayList<>();
		Mockito.when(service.getAllUsers()).thenReturn(allUsers);
		mockMvc.perform(get("/users")).andExpect(status().isNoContent());
	}

	@Test
	public void testGetUserById() throws Exception {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(service.getUserById(testUser.getId())).thenReturn(testUser);
		mockMvc.perform(get("/users/1")).andExpect(status().isFound()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Alex")));
	}

	@Test
	public void testAddUser_success() throws Exception {
		User testUser = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(service.addUser(testUser)).thenReturn(testUser);
		mockMvc.perform(post("/users/create").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(this.mapper.writeValueAsString(testUser)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void testAddUser_failure() throws Exception {
		User testUser = new User("Alex", "alex", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(service.addUser(testUser)).thenReturn(testUser);
		mockMvc.perform(post("/users/create").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(this.mapper.writeValueAsString(testUser)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateUser_success() throws Exception {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(service.updateUser(testUser, testUser.getId())).thenReturn(testUser);
		mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(mapper.writeValueAsString(testUser)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser_failure() throws Exception {
		User testUser = new User(1, "Alex", "alex", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.when(service.updateUser(testUser, testUser.getId())).thenReturn(testUser);
		mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(mapper.writeValueAsString(testUser)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testDeleteUser() throws Exception {
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		Mockito.doNothing().when(service).deleteUser(testUser.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

}
