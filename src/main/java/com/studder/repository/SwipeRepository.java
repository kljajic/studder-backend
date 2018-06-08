package com.studder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studder.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

	public Swipe getSwipeByLikerIdAndLikedId(Long likerId, Long likedId);
	
	@Query("select count(swipe.id) from Swipe swipe where swipe.liker.id = ?1")
	int getSwipesCountForUser(Long likerId);
	
}
