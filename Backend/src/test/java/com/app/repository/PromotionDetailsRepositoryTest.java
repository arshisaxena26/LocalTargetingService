package com.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

@RunWith(SpringRunner.class)
@DataJpaTest
public class PromotionDetailsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PromotionDetailsRepository repository;

	@Test
	public void testFindAllPromotionsEmptyList() {
		Iterable<PromotionDetails> promotions = repository.findAll();
		assertThat(promotions).isEmpty();
	}

	@Test
	public void testSavePromotion() {
		PromotionDetails testPromotion = repository.save(new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now()));

		assertThat(testPromotion).hasFieldOrPropertyWithValue("name", "PromoA");
		assertThat(testPromotion).hasFieldOrPropertyWithValue("status", Status.PENDING);
		assertThat(testPromotion).hasFieldOrPropertyWithValue("minAge", 0);
		assertThat(testPromotion).hasFieldOrPropertyWithValue("maxAge", 20);
		assertThat(testPromotion).hasFieldOrPropertyWithValue("gender", Gender.M);
	}

	@Test
	public void testSaveAllPromotions() {
		List<PromotionDetails> promotions = new ArrayList<>();
		promotions.add(new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M, LocalDateTime.now(),
				LocalDateTime.now()));
		promotions.add(new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F, LocalDateTime.now(),
				LocalDateTime.now()));
		promotions.add(new PromotionDetails("PromoC", Status.PENDING, 40, 60, Gender.O, LocalDateTime.now(),
				LocalDateTime.now()));

		List<PromotionDetails> savedPromotions = repository.saveAll(promotions);

		assertThat(savedPromotions).hasSize(3);

	}

	@Test
	public void testFindAllPromotions() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails promotion3 = new PromotionDetails("PromoC", Status.PENDING, 40, 60, Gender.O,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion3);

		Iterable<PromotionDetails> promotions = repository.findAll();

		assertThat(promotions).hasSize(3).contains(promotion1, promotion2, promotion3);
	}

	@Test
	public void testFindPromotionById() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails foundPromotion = repository.findById(promotion2.getId()).get();

		assertThat(foundPromotion).isEqualTo(promotion2);
	}

	@Test
	public void testUpdatePromotion() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails updatedPromotion = new PromotionDetails("PromoB1", Status.COMPLETED, 25, 45, Gender.O,
				LocalDateTime.now(), LocalDateTime.now());

		PromotionDetails promotion = repository.findById(promotion2.getId()).get();
		promotion.setName(updatedPromotion.getName());
		promotion.setStatus(updatedPromotion.getStatus());
		promotion.setMinAge(updatedPromotion.getMinAge());
		promotion.setMaxAge(updatedPromotion.getMaxAge());
		promotion.setGender(updatedPromotion.getGender());
		promotion.setStartTimeEpoch(updatedPromotion.getStartTimeEpoch());
		promotion.setEndTimeEpoch(updatedPromotion.getEndTimeEpoch());
		repository.save(promotion);

		PromotionDetails checkPromotion = repository.findById(promotion2.getId()).get();

		assertThat(checkPromotion.getName()).isEqualTo(updatedPromotion.getName());
		assertThat(checkPromotion.getStatus()).isEqualTo(updatedPromotion.getStatus());
		assertThat(checkPromotion.getMinAge()).isEqualTo(updatedPromotion.getMinAge());
		assertThat(checkPromotion.getMaxAge()).isEqualTo(updatedPromotion.getMaxAge());
		assertThat(checkPromotion.getGender()).isEqualTo(updatedPromotion.getGender());
		assertThat(checkPromotion.getStartTimeEpoch()).isEqualTo(updatedPromotion.getStartTimeEpoch());
		assertThat(checkPromotion.getEndTimeEpoch()).isEqualTo(updatedPromotion.getEndTimeEpoch());
	}

	@Test
	public void testDeletePromotion() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails promotion3 = new PromotionDetails("PromoC", Status.PENDING, 40, 60, Gender.O,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion3);

		repository.delete(promotion2);

		Iterable<PromotionDetails> promotions = repository.findAll();

		assertThat(promotions).hasSize(2).contains(promotion1, promotion3);
	}

	@Test
	public void testFindByStatusAndStartTimeEpochLessThanEqual() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now().minusDays(1), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails promotion3 = new PromotionDetails("PromoC", Status.COMPLETED, 40, 60, Gender.O,
				LocalDateTime.now().plusDays(4), LocalDateTime.now());
		entityManager.persist(promotion3);

		List<PromotionDetails> promotions = repository.findByStatusAndStartTimeEpochLessThanEqual(Status.PENDING,
				LocalDateTime.now());

		assertThat(promotions).hasSize(2).contains(promotion1, promotion2);
	}

	@Test
	public void testFindByStatus() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now().minusDays(1), LocalDateTime.now());
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails promotion3 = new PromotionDetails("PromoC", Status.COMPLETED, 40, 60, Gender.O,
				LocalDateTime.now().plusDays(4), LocalDateTime.now());
		entityManager.persist(promotion3);

		List<PromotionDetails> promotions = repository.findByStatus(Status.PENDING);

		assertThat(promotions).hasSize(2).contains(promotion1, promotion2);
	}

	@Test
	public void testFindByEndTimeEpochLessThan() {
		PromotionDetails promotion1 = new PromotionDetails("PromoA", Status.PENDING, 0, 20, Gender.M,
				LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(5));
		entityManager.persist(promotion1);

		PromotionDetails promotion2 = new PromotionDetails("PromoB", Status.PENDING, 20, 40, Gender.F,
				LocalDateTime.now().minusDays(10), LocalDateTime.now());
		entityManager.persist(promotion2);

		PromotionDetails promotion3 = new PromotionDetails("PromoC", Status.COMPLETED, 40, 60, Gender.O,
				LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
		entityManager.persist(promotion3);

		List<PromotionDetails> promotions = repository.findByEndTimeEpochLessThan(LocalDateTime.now());

		assertThat(promotions).hasSize(2).contains(promotion1, promotion2);
	}

}
