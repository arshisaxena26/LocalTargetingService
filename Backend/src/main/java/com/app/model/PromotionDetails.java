package com.app.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.app.enums.Gender;
import com.app.enums.Status;

@Entity
@Table
public class PromotionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Name is mandatory")
	@Column(length = 50)
	private String name;

	@NotNull(message = "Status is mandatory")
	private Status status;

	@PositiveOrZero(message = "Age cannot be negative")
	@Max(value = 150, message = "Age cannot be more than 150")
	private int minAge;

	@PositiveOrZero(message = "Age cannot be negative")
	@Max(value = 150, message = "Age cannot be more than 150")
	private int maxAge;

	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@NotNull(message = "Start Time is mandatory")
	private LocalDateTime startTimeEpoch;

	@NotNull(message = "End Time is mandatory")
	private LocalDateTime endTimeEpoch;

	@OneToMany(mappedBy = "promotion")
	private List<TargetedOffer> targetedOffer;

	public PromotionDetails() {
		// TODO Auto-generated constructor stub
	}

	public PromotionDetails(Integer id, String name, Status status, int minAge, int maxAge, Gender gender,
			LocalDateTime startTimeEpoch, LocalDateTime endTimeEpoch) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.gender = gender;
		this.startTimeEpoch = startTimeEpoch;
		this.endTimeEpoch = endTimeEpoch;
	}

	public PromotionDetails(String name, Status status, int minAge, int maxAge, Gender gender,
			LocalDateTime startTimeEpoch, LocalDateTime endTimeEpoch) {
		this.name = name;
		this.status = status;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.gender = gender;
		this.startTimeEpoch = startTimeEpoch;
		this.endTimeEpoch = endTimeEpoch;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDateTime getStartTimeEpoch() {
		return startTimeEpoch;
	}

	public void setStartTimeEpoch(LocalDateTime startTimeEpoch) {
		this.startTimeEpoch = startTimeEpoch;
	}

	public LocalDateTime getEndTimeEpoch() {
		return endTimeEpoch;
	}

	public void setEndTimeEpoch(LocalDateTime endTimeEpoch) {
		this.endTimeEpoch = endTimeEpoch;
	}

	public List<TargetedOffer> getTargetedOffer() {
		return targetedOffer;
	}

	public void setTargetedOffer(List<TargetedOffer> targetedOffer) {
		this.targetedOffer = targetedOffer;
	}

	@Override
	public String toString() {
		return "PromotionDetails [id=" + id + ", name=" + name + ", minAge=" + minAge + ", maxAge=" + maxAge
				+ ", gender=" + gender + ", status=" + status + ", startTimeEpoch=" + startTimeEpoch + ", endTimeEpoch="
				+ endTimeEpoch + "]";
	}
}
