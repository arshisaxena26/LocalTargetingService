package com.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.app.enums.Status;
import com.app.model.PromotionDetails;
import com.app.serviceImpl.PromotionDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PromotionDetailsController.class)
public class PromotionDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PromotionDetailsServiceImpl service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testGetPromotions() throws Exception {
		List<PromotionDetails> allPromotions = new ArrayList<>();
		allPromotions.add(new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M, LocalDateTime.now(),
				LocalDateTime.now()));
		Mockito.when(service.getAllPromotions()).thenReturn(allPromotions);
		mockMvc.perform(get("/promotions")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.equalTo("PromoA")));
	}

	@Test
	public void testGetPromotionsEmptyList() throws Exception {
		List<PromotionDetails> allPromotions = new ArrayList<>();
		Mockito.when(service.getAllPromotions()).thenReturn(allPromotions);
		mockMvc.perform(get("/promotions")).andExpect(status().isNoContent());
	}

	@Test
	public void testGetPromotionById() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(service.getPromotionById(testpromotion.getId())).thenReturn(testpromotion);
		mockMvc.perform(get("/promotions/1")).andExpect(status().isFound()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("PromoA")));
	}

	@Test
	public void testAddPromotion_success() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(service.addPromotion(testpromotion)).thenReturn(testpromotion);
		mockMvc.perform(post("/promotions/create").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(this.mapper.writeValueAsString(testpromotion)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void testAddPromotion_failure() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails("PromoA", Status.PENDING, -9, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(service.addPromotion(testpromotion)).thenReturn(testpromotion);
		mockMvc.perform(post("/promotions/create").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(this.mapper.writeValueAsString(testpromotion)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdatePromotion_success() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(service.updatePromotion(testpromotion, testpromotion.getId())).thenReturn(testpromotion);
		mockMvc.perform(put("/promotions/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(mapper.writeValueAsString(testpromotion)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdatePromotion_failure() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, -78, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(service.updatePromotion(testpromotion, testpromotion.getId())).thenReturn(testpromotion);
		mockMvc.perform(put("/promotions/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(mapper.writeValueAsString(testpromotion)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testDeletePromotion() throws Exception {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.doNothing().when(service).deletePromotionById(testpromotion.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete("/promotions/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
}
