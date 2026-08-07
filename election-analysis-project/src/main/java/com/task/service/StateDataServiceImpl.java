package com.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.task.dto.StateWiseData;
import com.task.model.CandidateResult;
import com.task.model.ElectionResult;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateDataServiceImpl implements StateDataService{

	private final ElectionDataService dataService;
  	private List<ElectionResult> winner;
  	private List<CandidateResult> candidates;

  	
  	@PostConstruct
  	public void initialize() {
  		winner = dataService.getWinners();
  		candidates = dataService.getCandidates();
  	}
	
	@Override
	public StateWiseData stateData(String state) {
		
		List<CandidateResult> stateCandidates = candidates.stream()
		.filter(s -> s.getState().equalsIgnoreCase(state))
		.toList();
		
		List<ElectionResult> stateWinners = winner.stream()
		.filter(e-> e.getState().equalsIgnoreCase(state))
		.toList();
		
		if(stateCandidates.isEmpty()) {
			return null;
		}
		
		return StateWiseData.builder()
				.state(state)
				.constitutions(stateCandidates.stream()
						.map(CandidateResult:: getConstituency)
						.distinct().toList())
				.candidates(stateCandidates.stream()
						.map(CandidateResult::getCandidate)
						.toList())
				.winCandidates(stateWinners.stream()
						.map(ElectionResult::getCandidate)
						.toList())
				.totalVotes(stateCandidates.stream()
						.mapToLong(CandidateResult::getTotalVotes)
						.sum())
				.build();
				
		
	}

}
