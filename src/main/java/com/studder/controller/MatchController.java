package com.studder.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.UserMatch;
import com.studder.service.MatchService;

@RestController
@RequestMapping("/matches")
public class MatchController {

	private final MatchService matchService;
	
	@Autowired
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}
	
	@GetMapping
	public List<UserMatch> getMatches() {
		return matchService.getMathces();
	}
	
	@GetMapping("/{matchId}")
	public UserMatch getMatch(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		return matchService.getMatch(matchId);
	}
	
	@DeleteMapping("/{matchId}")
	public void deleteMatch(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		matchService.deleteMathc(matchId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getMatches/{userId}")
	public List<UserMatch> getMatches(@PathVariable("userId") @NotNull Long userId){
		List<UserMatch> matches = matchService.getMatches(userId);
		return matches;
	}
	
}
