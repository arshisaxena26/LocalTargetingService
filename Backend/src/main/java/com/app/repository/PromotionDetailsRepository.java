package com.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.enums.Status;
import com.app.model.PromotionDetails;

public interface PromotionDetailsRepository extends JpaRepository<PromotionDetails, Integer> {

	public List<PromotionDetails> findByStatusAndStartTimeEpochLessThanEqual(Status status,
			LocalDateTime startTimeEpoch);

	public List<PromotionDetails> findByStatus(Status status);

	public List<PromotionDetails> findByEndTimeEpochLessThan(LocalDateTime currentDate);

}
