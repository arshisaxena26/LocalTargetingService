package com.app.service;

import java.util.List;

import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;

public interface ITargetedOfferService {

	public List<User> getPromotionAvailedUsers(int id);

	public void matchOffers(List<PromotionDetails> promotions);

	public List<TargetedOffer> getAllOffers();

	public TargetedOffer getOfferById(int id);

	public void deleteOfferById(int id);
}
