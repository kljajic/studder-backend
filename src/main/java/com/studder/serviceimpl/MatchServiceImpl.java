package com.studder.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.Match;
import com.studder.repository.MatchRepository;
import com.studder.service.MatchService;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	private final MatchRepository matchRepository;
	
	@Autowired
	public MatchServiceImpl(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}
	
	@Override
	public void createMatch(Match match) {
		matchRepository.save(match);
	}

	@Override
	public Match getMathc(Long matchId) {
		return matchRepository.getOne(matchId);
	}

	@Override
	public List<Match> getMathces(Long userId) {
		//userId should be get from context holder
		return matchRepository.getMatchesByParticipant1IdOrParticipant2Id(userId);
	}

	@Override
	public void deleteMathc(Long mathcId) {
		matchRepository.deleteById(mathcId);
	}

}
