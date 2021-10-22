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

import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;
import com.app.repository.TargetedOfferRepository;
import com.app.serviceImpl.TargetedOfferServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TargetedOfferServiceImplTest {

	@Mock
	private TargetedOfferRepository repository;

	@InjectMocks
	private TargetedOfferServiceImpl service;

	@Test
	public void testGetAllOffers() {
		List<TargetedOffer> allOffers = new ArrayList<>();
		allOffers.add(new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails()));
		Mockito.when(repository.findAll()).thenReturn(allOffers);
		List<TargetedOffer> fetchedOffers = service.getAllOffers();
		assertThat(fetchedOffers.size()).isGreaterThan(0);
	}

	@Test
	public void testGetOfferById() {
		TargetedOffer testOffer = new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails());
		Mockito.when(repository.findById(testOffer.getId())).thenReturn(Optional.of(testOffer));
		assertThat(service.getOfferById(1)).isEqualTo(testOffer);
	}

	@Test
	public void testDeleteOffer() {
		TargetedOffer testOffer = new TargetedOffer(1, LocalDateTime.now(), new User(), new PromotionDetails());
		repository.deleteById(testOffer.getId());
		verify(repository, times(1)).deleteById(testOffer.getId());
	}
}
