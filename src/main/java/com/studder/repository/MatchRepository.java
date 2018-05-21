package com.studder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studder.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	@Query("select match from Match match where match.participant1.id = ?1 or match.participant2.id = ?1")
	List<Match> getMatchesByParticipant1IdOrParticipant2Id(Long participantId);
	
}
