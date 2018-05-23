package com.studder.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.UserMatch;
import com.studder.repository.MatchRepository;
import com.studder.service.MatchService;
import com.studder.service.UserService;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	private final MatchRepository matchRepository;
	private final UserService userService;
	
	@Autowired
	public MatchServiceImpl(MatchRepository matchRepository, UserService userService) {
		this.matchRepository = matchRepository;
		this.userService = userService;
	}
	
	@Override
	public void createMatch(UserMatch match) {
		matchRepository.save(match);
	}

	@Override
	@Transactional(readOnly = true)
	public UserMatch getMathc(Long matchId) {
		return matchRepository.getOne(matchId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserMatch> getMathces() {
		Long userId1 = userService.getLoggedUser().getId();
		return matchRepository.getMatchesByParticipant1IdOrParticipant2Id(userId1);
	}

	@Override
	public void deleteMathc(Long mathcId) {
		matchRepository.deleteById(mathcId);
	}

}
