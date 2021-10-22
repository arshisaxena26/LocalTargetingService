package com.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.enums.Gender;
import com.app.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void testFindAllUsersEmptyList() {
		Iterable<User> users = repository.findAll();
		assertThat(users).isEmpty();
	}

	@Test
	public void testSaveUser() {
		User testUser = repository.save(new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M));

		assertThat(testUser).hasFieldOrPropertyWithValue("name", "Alex");
		assertThat(testUser).hasFieldOrPropertyWithValue("email", "alex@lts.com");
		assertThat(testUser).hasFieldOrPropertyWithValue("dob", LocalDate.parse("2020-02-02"));
		assertThat(testUser).hasFieldOrPropertyWithValue("gender", Gender.M);
	}

	@Test
	public void testFindAllUsers() {
		User user1 = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		entityManager.persist(user1);

		User user2 = new User("Stacy", "stacy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);
		entityManager.persist(user2);

		User user3 = new User("Joe", "joe@lts.com", LocalDate.parse("1993-02-02"), Gender.O);
		entityManager.persist(user3);

		Iterable<User> users = repository.findAll();

		assertThat(users).hasSize(3).contains(user1, user2, user3);
	}

	@Test
	public void testFindUserById() {
		User user1 = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		entityManager.persist(user1);

		User user2 = new User("Stacy", "stacy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);
		entityManager.persist(user2);

		User foundUser = repository.findById(user2.getId()).get();

		assertThat(foundUser).isEqualTo(user2);
	}

	@Test
	public void testUpdateUser() {
		User user1 = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		entityManager.persist(user1);

		User user2 = new User("Stacy", "stacy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);
		entityManager.persist(user2);

		User updatedUser = new User("Tracy", "tracy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);

		User user = repository.findById(user2.getId()).get();
		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setDob(updatedUser.getDob());
		user.setGender(updatedUser.getGender());
		repository.save(user);

		User checkUser = repository.findById(user2.getId()).get();

		assertThat(checkUser.getName()).isEqualTo(updatedUser.getName());
		assertThat(checkUser.getEmail()).isEqualTo(updatedUser.getEmail());
		assertThat(checkUser.getDob()).isEqualTo(updatedUser.getDob());
		assertThat(checkUser.getGender()).isEqualTo(updatedUser.getGender());
	}

	@Test
	public void testDeleteUser() {
		User user1 = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.M);
		entityManager.persist(user1);

		User user2 = new User("Stacy", "stacy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);
		entityManager.persist(user2);

		User user3 = new User("Joe", "joe@lts.com", LocalDate.parse("1993-02-02"), Gender.O);
		entityManager.persist(user3);

		repository.delete(user2);

		Iterable<User> users = repository.findAll();

		assertThat(users).hasSize(2).contains(user1, user3);
	}

	@Test
	public void testFindByGenderAndDobBetween() {
		User user1 = new User("Alex", "alex@lts.com", LocalDate.parse("2020-02-02"), Gender.F);
		entityManager.persist(user1);

		User user2 = new User("Stacy", "stacy@lts.com", LocalDate.parse("2000-02-02"), Gender.F);
		entityManager.persist(user2);

		User user3 = new User("Joe", "joe@lts.com", LocalDate.parse("1993-02-02"), Gender.O);
		entityManager.persist(user3);

		Iterable<User> users = repository.findByGenderAndDobBetween(Gender.F, LocalDate.parse("2000-02-02"),
				LocalDate.parse("2020-02-02"));

		assertThat(users).hasSize(2).contains(user1, user2);
	}

}
