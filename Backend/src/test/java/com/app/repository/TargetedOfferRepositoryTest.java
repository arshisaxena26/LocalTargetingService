package com.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.enums.Gender;
import com.app.enums.Status;
import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TargetedOfferRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TargetedOfferRepository repository;

	@Test
	public void testFindAllOffersEmptyList() {
		Iterable<TargetedOffer> offers = repository.findAll();
		assertThat(offers).isEmpty();
	}

	@Test
	public void testFindAllOffers() {
		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), null, null);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		TargetedOffer offer3 = new TargetedOffer(LocalDateTime.now().minusDays(1), null, null);
		entityManager.persist(offer3);

		Iterable<TargetedOffer> offers = repository.findAll();

		assertThat(offers).hasSize(3).contains(offer1, offer2, offer3);
	}

	@Test
	public void testFindOfferById() {
		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), null, null);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		TargetedOffer foundOffer = repository.findById(offer2.getId()).get();

		assertThat(foundOffer).isEqualTo(offer2);
	}

	@Test
	public void testSaveAllOffers() {
		List<TargetedOffer> offers = new ArrayList<>();
		offers.add(new TargetedOffer(LocalDateTime.now(), null, null));
		offers.add(new TargetedOffer(LocalDateTime.now().plusDays(1), null, null));
		offers.add(new TargetedOffer(LocalDateTime.now().minusDays(1), null, null));

		List<TargetedOffer> savedOffers = repository.saveAll(offers);

		assertThat(savedOffers).hasSize(3);

	}

	@Test
	public void testDeleteOfferById() {
		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), null, null);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		TargetedOffer offer3 = new TargetedOffer(LocalDateTime.now().minusDays(1), null, null);
		entityManager.persist(offer3);

		repository.deleteById(offer2.getId());

		Iterable<TargetedOffer> offers = repository.findAll();

		assertThat(offers).hasSize(2).contains(offer1, offer3);
	}

	@Test
	public void testFindByPromotion() {
		PromotionDetails promotion = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion);

		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), null, promotion);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		List<TargetedOffer> offers = repository.findByPromotion(promotion);

		assertThat(offers).hasSize(1).contains(offer1);

	}

	@Test
	public void testDeleteByPromotion() {
		PromotionDetails promotion = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion);

		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), null, promotion);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		repository.deleteByPromotion(promotion);

		List<TargetedOffer> offers = repository.findAll();

		assertThat(offers).hasSize(1).contains(offer2);
	}

	@Test
	public void testDeleteByUser() {
		User user = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		entityManager.persist(user);

		TargetedOffer offer1 = new TargetedOffer(LocalDateTime.now(), user, null);
		entityManager.persist(offer1);

		TargetedOffer offer2 = new TargetedOffer(LocalDateTime.now().plusDays(1), null, null);
		entityManager.persist(offer2);

		repository.deleteByUser(user);

		List<TargetedOffer> offers = repository.findAll();

		assertThat(offers).hasSize(1).contains(offer2);
	}

}
