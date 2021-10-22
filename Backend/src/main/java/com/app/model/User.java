package com.app.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.app.enums.Gender;

@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Name is mandatory")
	@Column(length = 50)
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email Format Incorrect")
	@Column(length = 50)
	private String email;

	@Past(message = "DOB cannot be in the future")
	private LocalDate dob;

	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@OneToMany(mappedBy = "user")
	private List<TargetedOffer> targetedOffer;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, String email, LocalDate dob, Gender gender) {
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
	}

	public User(Integer id, String name, String email, LocalDate dob, Gender gender) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<TargetedOffer> getTargetedOffer() {
		return targetedOffer;
	}

	public void setTargetedOffer(List<TargetedOffer> targetedOffer) {
		this.targetedOffer = targetedOffer;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", dob=" + dob + ", gender=" + gender + "]";
	}
}
