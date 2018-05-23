package com.studder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studder.model.Gender;
import com.studder.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByUsername(String username);
	
	@Query("select user from User user where user.id != ?1 and user.isDeactivated = false and user.userGender = ?2 "
			+ "and user.id not in (select swipe.liked.id from Swipe swipe where swipe.liker.id = ?1)")
	List<User> getUsersForSwiping(Long userId, Gender swipeThrow);
	
}
