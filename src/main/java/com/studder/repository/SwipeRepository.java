package com.studder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studder.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

	public Swipe getSwipeByLikerIdAndLikedId(Long likerId, Long likedId);
	
}
