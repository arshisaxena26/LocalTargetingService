package com.app.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.enums.Status;
import com.app.model.PromotionDetails;
import com.app.repository.PromotionDetailsRepository;
import com.app.repository.TargetedOfferRepository;
import com.app.repository.UserRepository;
import com.app.service.IPromotionDetailsService;

@Service
@Transactional
public class PromotionDetailsServiceImpl implements IPromotionDetailsService {

	@Autowired
	private PromotionDetailsRepository promotionRepo;

	@Autowired
	private TargetedOfferRepository offerRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<PromotionDetails> getAllPromotions() {
		List<PromotionDetails> promotions = new ArrayList<>();
		promotionRepo.findAll().forEach(p -> promotions.add(new PromotionDetails(p.getId(), p.getName(), p.getStatus(),
				p.getMinAge(), p.getMaxAge(), p.getGender(), p.getStartTimeEpoch(), p.getEndTimeEpoch())));
		return promotions;
	}

	@Override
	public PromotionDetails getPromotionById(int id) {
		PromotionDetails promotion = promotionRepo.findById(id).get();
		return new PromotionDetails(promotion.getId(), promotion.getName(), promotion.getStatus(),
				promotion.getMinAge(), promotion.getMaxAge(), promotion.getGender(), promotion.getStartTimeEpoch(),
				promotion.getEndTimeEpoch());
	}

	@Override
	public PromotionDetails addPromotion(PromotionDetails promotion) {
		return promotionRepo.save(promotion);
	}

	@Override
	public PromotionDetails updatePromotion(PromotionDetails promotion, int id) {
		PromotionDetails updatedPromotion = getPromotionById(id);
		updatedPromotion.setEndTimeEpoch(promotion.getEndTimeEpoch());
		updatedPromotion.setGender(promotion.getGender());
		updatedPromotion.setMaxAge(promotion.getMaxAge());
		updatedPromotion.setMinAge(promotion.getMinAge());
		updatedPromotion.setName(promotion.getName());
		updatedPromotion.setStartTimeEpoch(promotion.getEndTimeEpoch());
		updatedPromotion.setStatus(promotion.getStatus());

		return promotionRepo.save(updatedPromotion);
	}

	@Override
	public void deletePromotionById(int id) {
		PromotionDetails promotion = promotionRepo.findById(id).get();
		offerRepo.deleteByPromotion(promotion);
		promotionRepo.delete(promotion);
	}

	@Override
	public List<PromotionDetails> getPendingPromotions(int executeJobsCount) {
		return promotionRepo.findByStatusAndStartTimeEpochLessThanEqual(Status.PENDING, LocalDateTime.now()).stream()
				.sorted((p1, p2) -> p1.getStartTimeEpoch().compareTo(p2.getStartTimeEpoch())).limit(executeJobsCount)
				.toList();
	}

	@Override
	public void updateStatusToProcessing(List<PromotionDetails> promotions) {
		promotions.forEach(p -> p.setStatus(Status.PROCESSING));
		promotionRepo.saveAll(promotions);
	}

	@Override
	public void updateStatusToCompleted(List<PromotionDetails> promotions) {
		promotions.stream()
				.filter(p -> userRepo
						.findByGenderAndDobBetween(p.getGender(), LocalDate.now().minusYears(p.getMaxAge()),
								LocalDate.now().minusYears(p.getMinAge()))
						.size() == offerRepo.findByPromotion(p).size())
				.forEach(p -> p.setStatus(Status.COMPLETED));
		promotionRepo.saveAll(promotions);
	}

	@Override
	public void overduePromotions() {
		List<PromotionDetails> overduePromotions = promotionRepo.findByStatus(Status.PROCESSING).stream()
				.filter(p -> ChronoUnit.HOURS.between(p.getStartTimeEpoch(), LocalDateTime.now()) > 1).toList();
		overduePromotions.forEach(p -> p.setStatus(Status.OVERDUE));
		promotionRepo.saveAll(overduePromotions);
	}

	@Override
	public void expiredPromotions() {
		List<PromotionDetails> expiredPromotions = promotionRepo.findByEndTimeEpochLessThan(LocalDateTime.now());
		expiredPromotions.forEach(p -> p.setStatus(Status.EXPIRED));
		promotionRepo.saveAll(expiredPromotions);
	}
}
