package com.studder.service;

import java.util.List;

import com.studder.model.Match;

public interface MatchService {

	void createMatch(Match match);
	
	Match getMathc(Long matchId);
	
	List<Match> getMathces(Long userId);
	
	void deleteMathc(Long mathcId);
	
}
