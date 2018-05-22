package com.studder.service;

import java.util.List;

import com.studder.model.UserMatch;

public interface MatchService {

	void createMatch(UserMatch match);
	
	UserMatch getMathc(Long matchId);
	
	List<UserMatch> getMathces(Long userId);
	
	void deleteMathc(Long mathcId);
	
}
