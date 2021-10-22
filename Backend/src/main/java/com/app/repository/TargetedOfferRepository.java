package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;

public interface TargetedOfferRepository extends JpaRepository<TargetedOffer, Integer> {

	public List<TargetedOffer> findByPromotion(PromotionDetails promotion);

	public void deleteByPromotion(PromotionDetails promotion);

	public void deleteByUser(User user);
}
