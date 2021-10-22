package com.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
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
import com.app.enums.Status;
import com.app.model.PromotionDetails;
import com.app.repository.PromotionDetailsRepository;
import com.app.serviceImpl.PromotionDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PromotionDetailsServiceImplTest {

	@Mock
	private PromotionDetailsRepository repository;

	@InjectMocks
	private PromotionDetailsServiceImpl service;

	@Test
	public void testGetAllPromotions() {
		List<PromotionDetails> allPromotions = new ArrayList<>();
		allPromotions.add(new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M, LocalDateTime.now(),
				LocalDateTime.now()));
		Mockito.when(repository.findAll()).thenReturn(allPromotions);
		List<PromotionDetails> fetchedPromotions = service.getAllPromotions();
		assertThat(fetchedPromotions.size()).isGreaterThan(0);
	}

	@Test
	public void testGetPromotionById() {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(repository.findById(testpromotion.getId())).thenReturn(Optional.of(testpromotion));
		assertThat(service.getPromotionById(1)).isNotNull();
	}

	@Test
	public void testAddPromotion() {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(repository.save(testpromotion)).thenReturn(testpromotion);
		assertThat(repository.save(testpromotion).getName()).isNotNull();
	}

	@Test
	public void testUpdatePromotion() {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		Mockito.when(repository.save(testpromotion)).thenReturn(testpromotion);
		assertThat(repository.save(testpromotion).getName()).isNotNull();
	}

	@Test
	public void testDeletePromotion() {
		PromotionDetails testpromotion = new PromotionDetails(1, "PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		repository.deleteById(testpromotion.getId());
		verify(repository, times(1)).deleteById(testpromotion.getId());
	}
}
