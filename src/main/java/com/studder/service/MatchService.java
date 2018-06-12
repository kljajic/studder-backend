package com.studder.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.studder.model.UserMatch;

public interface MatchService {

	UserMatch createMatch(UserMatch match);
	
	UserMatch getMathc(Long matchId);
	
	List<UserMatch> getMathces();
	
	void deleteMathc(Long mathcId);
	
	List<UserMatch> getMatches(Long userId);

	UserMatch getMatch(@NotNull @Valid Long matchId);
	
	void notifyMatched(UserMatch match);
	
	Integer getMatchesCountForUser(Long userId);
	
}
