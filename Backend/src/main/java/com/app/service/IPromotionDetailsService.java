package com.app.service;

import java.util.List;

import com.app.model.PromotionDetails;

public interface IPromotionDetailsService {

	public List<PromotionDetails> getAllPromotions();

	public PromotionDetails getPromotionById(int id);

	public PromotionDetails addPromotion(PromotionDetails promotion);

	public PromotionDetails updatePromotion(PromotionDetails promotion, int id);

	public void deletePromotionById(int id);

	public List<PromotionDetails> getPendingPromotions(int executeJobsCount);

	public void updateStatusToProcessing(List<PromotionDetails> promotions);

	public void updateStatusToCompleted(List<PromotionDetails> promotions);

	public void overduePromotions();

	public void expiredPromotions();
}
