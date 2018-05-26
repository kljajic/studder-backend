package com.studder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studder.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
	
	Media findMediaByName(String name);
	List<Media> getMediaByUserId(Long userId);
	
}
