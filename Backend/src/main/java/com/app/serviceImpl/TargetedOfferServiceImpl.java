package com.app.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;
import com.app.repository.PromotionDetailsRepository;
import com.app.repository.TargetedOfferRepository;
import com.app.repository.UserRepository;
import com.app.service.ITargetedOfferService;

@Service
@Transactional
public class TargetedOfferServiceImpl implements ITargetedOfferService {

	@Autowired
	private PromotionDetailsRepository promotionRepo;

	@Autowired
	private TargetedOfferRepository offerRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> getPromotionAvailedUsers(int id) {
		List<User> users = new ArrayList<>();
		offerRepo.findByPromotion(promotionRepo.findById(id).get())
				.forEach(offer -> users.add(new User(offer.getUser().getName(), offer.getUser().getEmail(),
						offer.getUser().getDob(), offer.getUser().getGender())));
		return users;
	}

	@Override
	public void matchOffers(List<PromotionDetails> promotions) {
		List<TargetedOffer> offers = new ArrayList<>();
		promotions.forEach(p -> userRepo
				.findByGenderAndDobBetween(p.getGender(), LocalDate.now().minusYears(p.getMaxAge()),
						LocalDate.now().minusYears(p.getMinAge()))
				.forEach(u -> offers.add(new TargetedOffer(LocalDateTime.now(), u, p))));
		offerRepo.saveAll(offers);
	}

	@Override
	public List<TargetedOffer> getAllOffers() {
		return offerRepo.findAll();
	}

	@Override
	public TargetedOffer getOfferById(int id) {
		return offerRepo.findById(id).get();
	}

	@Override
	public void deleteOfferById(int id) {
		offerRepo.deleteById(id);
	}
}
