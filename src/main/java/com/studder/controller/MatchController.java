package com.studder.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/{userId}")
	public List<UserMatch> getMatches(@PathVariable("userId") @NotNull @Valid Long userId) {
		return matchService.getMathces(userId);
	}
	
	@DeleteMapping("/{matchId}")
	public void deleteMatch(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		matchService.deleteMathc(matchId);
	}
	
}
