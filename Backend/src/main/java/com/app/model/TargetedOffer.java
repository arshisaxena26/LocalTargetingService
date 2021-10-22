package com.app.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class TargetedOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime creationTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "promotion_id")
	private PromotionDetails promotion;

	public TargetedOffer() {
		// TODO Auto-generated constructor stub
	}

	public TargetedOffer(LocalDateTime creationTime, User user, PromotionDetails promotion) {
		this.creationTime = creationTime;
		this.user = user;
		this.promotion = promotion;
	}

	public TargetedOffer(Integer id, LocalDateTime creationTime, User user, PromotionDetails promotion) {
		this.id = id;
		this.creationTime = creationTime;
		this.user = user;
		this.promotion = promotion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PromotionDetails getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionDetails promotion) {
		this.promotion = promotion;
	}

	@Override
	public String toString() {
		return "TargetedOffer [id=" + id + ", creationTime=" + creationTime + "]";
	}
}
