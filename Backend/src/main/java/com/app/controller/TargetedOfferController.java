package com.app.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.RecordNotFoundException;
import com.app.model.PromotionDetails;
import com.app.model.TargetedOffer;
import com.app.model.User;
import com.app.serviceImpl.PromotionDetailsServiceImpl;
import com.app.serviceImpl.TargetedOfferServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/offers")
public class TargetedOfferController {

	Logger logger = LogManager.getLogger("TargetedOfferController.class");

	@Autowired
	private TargetedOfferServiceImpl offerService;

	@Autowired
	private PromotionDetailsServiceImpl promotionService;

	@Scheduled(fixedRate = 300000)
	public void getScheduledPromotions() {
		int executeJobsCount = 5;
		List<PromotionDetails> promotions = promotionService.getPendingPromotions(executeJobsCount);
		promotionService.updateStatusToProcessing(promotions);
		offerService.matchOffers(promotions);
		promotionService.updateStatusToCompleted(promotions);
		promotionService.overduePromotions();
		promotionService.expiredPromotions();
		logger.info("Scheduled Promotions Request Completed");
	}

	@GetMapping("/promotion/{id}")
	public ResponseEntity<List<User>> getPromotionUsers(@PathVariable int id) {
		List<User> users = offerService.getPromotionAvailedUsers(id);
		if (users.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		logger.info("Promotion-Availed Users List Request Completed");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TargetedOffer>> getOffers() {
		List<TargetedOffer> offers = offerService.getAllOffers();
		if (offers.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		logger.info("Targeted Offers List Request Completed");
		return new ResponseEntity<>(offers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TargetedOffer> getTargetedOffer(@PathVariable int id) {
		try {
			logger.info("Targeted Offer Request Completed");
			return new ResponseEntity<>(offerService.getOfferById(id), HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOffer(@PathVariable int id) {
		try {
			offerService.deleteOfferById(id);
			logger.info("Offer Deletion Request Completed");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}

	}
}
