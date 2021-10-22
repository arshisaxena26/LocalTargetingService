package com.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.enums.Gender;
import com.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByGenderAndDobBetween(Gender gender, LocalDate startDate, LocalDate endDate);
}
