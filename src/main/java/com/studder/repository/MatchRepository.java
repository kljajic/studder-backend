package com.studder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studder.model.UserMatch;

@Repository
public interface MatchRepository extends JpaRepository<UserMatch, Long> {

	@Query("select match from UserMatch match where match.participant1.id = ?1 or match.participant2.id = ?1")
	List<UserMatch> getMatchesByParticipant1IdOrParticipant2Id(Long participantId);
	
	@Query("select count(match.id) from UserMatch match where match.participant1.id = ?1 or match.participant2.id = ?1")
	int getMathesCountForUser(Long participantId);
	
}
