package com.app.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.RecordNotFoundException;
import com.app.model.PromotionDetails;
import com.app.serviceImpl.PromotionDetailsServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/promotions")
public class PromotionDetailsController {

	Logger logger = LogManager.getLogger("PromotionDetailsController.class");

	@Autowired
	private PromotionDetailsServiceImpl service;

	@GetMapping
	public ResponseEntity<List<PromotionDetails>> getPromotions() {
		List<PromotionDetails> promotions = service.getAllPromotions();
		if (promotions.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		logger.info("Promotions List Request Completed");
		return new ResponseEntity<>(promotions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PromotionDetails> getPromotionDetails(@PathVariable int id) {
		try {
			logger.info("Promotion Details Request Completed");
			return new ResponseEntity<>(service.getPromotionById(id), HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> addPromotionDetails(@RequestBody @Valid PromotionDetails promotion) {
		logger.info("Add Promotion Request Completed");
		return new ResponseEntity<>(service.addPromotion(promotion), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PromotionDetails> updatePromotionDetails(@RequestBody @Valid PromotionDetails promotion,
			@PathVariable int id) {
		try {
			logger.info("Update Promotion Request Completed");
			return new ResponseEntity<>(service.updatePromotion(promotion, id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePromotion(@PathVariable int id) {
		try {
			service.deletePromotionById(id);
			logger.info("Promotion Deletion Request Completed");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.warn(e);
			throw new RecordNotFoundException("Invalid ID: " + id);
		}

	}
}
