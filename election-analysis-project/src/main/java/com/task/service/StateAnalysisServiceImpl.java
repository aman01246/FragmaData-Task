package com.task.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task.model.CandidateResult;
import com.task.model.ElectionResult;
import com.task.util.DataReader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class StateAnalysisServiceImpl implements StateAnalysisService {

	  private final DataReader dataReader;

	    private List<ElectionResult> winners;
	    private List<CandidateResult> candidates;
	    
	    @PostConstruct
	    public void loadData() {
	        winners = dataReader.readElectionResult("2019_Results_Winning_Candidate.csv");
	        candidates = dataReader.readCandidateResult("2019_Results.csv");
	    }

	    @Override
	    public List<String> getAllStates() {

	        return winners.stream()
	                .map(ElectionResult::getState)
	                .distinct()
	                .sorted()
	                .toList();
	    }

	    @Override
	    public List<ElectionResult> getWinningCandidatesByState(String state) {

	        return winners.stream()
	                .filter(e -> e.getState().equalsIgnoreCase(state))
	                .toList();
	    }
	    
	    @Override
	    public List<CandidateResult> getCandidatesByState(String state) {

	        return candidates.stream()
	                .filter(c -> c.getState().equalsIgnoreCase(state))
	                .toList();
	    }

	    @Override
	    public long getTotalVotesByState(String state) {

	        return candidates.stream()
	                .filter(c -> c.getState().equalsIgnoreCase(state))
	                .mapToLong(CandidateResult::getTotalVotes)
	                .sum();
	    }

	    @Override
	    public long getTotalConstituenciesByState(String state) {

	        return candidates.stream()
	                .filter(c -> c.getState().equalsIgnoreCase(state))
	                .map(CandidateResult::getConstituency)
	                .distinct()
	                .count();
	    }

	    @Override
	    public long getTotalCandidatesByState(String state) {

	        return candidates.stream()
	                .filter(c -> c.getState().equalsIgnoreCase(state))
	                .count();
	    }
	    
	    @Override
	    public Map<String, Long> getPartyWiseSeatsByState(String state) {

	        return winners.stream()
	                .filter(e -> e.getState().equalsIgnoreCase(state))
	                .collect(Collectors.groupingBy(
	                        ElectionResult::getParty,
	                        Collectors.counting()));
	    }

	    @Override
	    public Map<String, Long> getPartyWiseVotesByState(String state) {

	        return winners.stream()
	                .filter(e -> e.getState().equalsIgnoreCase(state))
	                .collect(Collectors.groupingBy(
	                        ElectionResult::getParty,
	                        Collectors.summingLong(ElectionResult::getVotes)));
	    }
	    

	    @Override
	    public List<ElectionResult> getAbsoluteMajorityWinnersByState(String state) {

	        return winners.stream()
	                .filter(e -> e.getState().equalsIgnoreCase(state))
	                .filter(e -> e.getPercentage() > 50)
	                .toList();
	    }
	    
}
