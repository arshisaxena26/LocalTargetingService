package com.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.app.model.TargetedOffer;
import com.app.model.User;
import com.app.serviceImpl.PromotionDetailsServiceImpl;
import com.app.serviceImpl.TargetedOfferServiceImpl;

@WebMvcTest(TargetedOfferController.class)
public class TargetedOfferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TargetedOfferServiceImpl offerService;

	@MockBean
	private PromotionDetailsServiceImpl promotionService;

	@Test
	public void testGetOffers() throws Exception {
		List<TargetedOffer> allOffers = new ArrayList<>();
		allOffers.add(new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails()));
		Mockito.when(offerService.getAllOffers()).thenReturn(allOffers);
		mockMvc.perform(get("/offers")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(1)));
	}

	@Test
	public void testGetOffersEmptyList() throws Exception {
		List<TargetedOffer> allOffers = new ArrayList<>();
		Mockito.when(offerService.getAllOffers()).thenReturn(allOffers);
		mockMvc.perform(get("/offers")).andExpect(status().isNoContent());
	}

	@Test
	public void testGetOfferById() throws Exception {
		TargetedOffer testOffer = new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails());
		Mockito.when(offerService.getOfferById(testOffer.getId())).thenReturn(testOffer);
		mockMvc.perform(get("/offers/1")).andExpect(status().isFound()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	public void testDeleteOffer() throws Exception {
		TargetedOffer testOffer = new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails());
		Mockito.doNothing().when(offerService).deleteOfferById(testOffer.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete("/offers/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetPromotionUsers_success() throws Exception {
		PromotionDetails testPromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		User testUser = new User(1, "Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		List<User> allUsers = new ArrayList<>();
		allUsers.add(testUser);
		Mockito.when(offerService.getPromotionAvailedUsers(testPromotion.getId())).thenReturn(allUsers);
		mockMvc.perform(get("/offers/promotion/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1))).andExpect(jsonPath("$[0].id", Matchers.equalTo(1)));
	}

	@Test
	public void testGetPromotionUsers_failure() throws Exception {
		PromotionDetails testPromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		List<User> allUsers = new ArrayList<>();
		Mockito.when(offerService.getPromotionAvailedUsers(testPromotion.getId())).thenReturn(allUsers);
		mockMvc.perform(get("/offers/promotion/1")).andExpect(status().isNoContent());
	}

	@Test
	public void testGetScheduledPromotions() throws Exception {
		List<PromotionDetails> promotions = new ArrayList<>();
		promotions.add(new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M, LocalDateTime.now(),
				LocalDateTime.now().plusDays(10)));
		Mockito.when(promotionService.getPendingPromotions(5)).thenReturn(promotions);
		Mockito.doNothing().when(promotionService).updateStatusToProcessing(promotions);
		Mockito.doNothing().when(offerService).matchOffers(promotions);
		Mockito.doNothing().when(promotionService).updateStatusToCompleted(promotions);
		Mockito.doNothing().when(promotionService).overduePromotions();
		Mockito.doNothing().when(promotionService).expiredPromotions();
	}
}
