package com.task.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task.model.CandidateResult;
import com.task.util.DataReader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class CandidateAnalysisServiceImpl implements CandidateAnalysisService{

	 private final DataReader dataReader;

	    private List<CandidateResult> candidates;

	    @PostConstruct
	    public void loadData() {
	        candidates = dataReader.readCandidateResult("candidate_result.csv");
	    }

	    @Override
	    public List<CandidateResult> getAllCandidates() {
	        return candidates;
	    }

	    @Override
	    public CandidateResult getCandidateByName(String candidateName) {

	        return candidates.stream()
	                .filter(c -> c.getCandidate().equalsIgnoreCase(candidateName))
	                .findFirst()
	                .orElse(null);
	    }

	    @Override
	    public List<CandidateResult> getCandidatesByState(String state) {

	        return candidates.stream()
	                .filter(c -> c.getState().equalsIgnoreCase(state))
	                .toList();
	    }

	    @Override
	    public List<CandidateResult> getCandidatesByConstituency(String constituency) {

	        return candidates.stream()
	                .filter(c -> c.getConstituency().equalsIgnoreCase(constituency))
	                .toList();
	    }
	    
	    @Override
	    public List<CandidateResult> getCandidatesByParty(String party) {

	        return candidates.stream()
	                .filter(c -> c.getParty().equalsIgnoreCase(party))
	                .toList();
	    }

	    @Override
	    public CandidateResult getCandidateWithHighestVotes() {

	        return candidates.stream()
	                .max(Comparator.comparingLong(CandidateResult::getTotalVotes))
	                .orElse(null);
	    }

	    @Override
	    public CandidateResult getCandidateWithLowestVotes() {

	        return candidates.stream()
	                .min(Comparator.comparingLong(CandidateResult::getTotalVotes))
	                .orElse(null);
	    }
	
	    @Override
	    public List<CandidateResult> getTopCandidatesByVotes(int limit) {

	        return candidates.stream()
	                .sorted(Comparator.comparingLong(CandidateResult::getTotalVotes).reversed())
	                .limit(limit)
	                .toList();
	    }

	    @Override
	    public List<CandidateResult> getBottomCandidatesByVotes(int limit) {

	        return candidates.stream()
	                .sorted(Comparator.comparingLong(CandidateResult::getTotalVotes))
	                .limit(limit)
	                .toList();
	    }

	    @Override
	    public long getTotalVotes() {

	        return candidates.stream()
	                .mapToLong(CandidateResult::getTotalVotes)
	                .sum();
	    }

	    @Override
	    public long getTotalEvmVotes() {

	        return candidates.stream()
	                .mapToLong(CandidateResult::getEvmVotes)
	                .sum();
	    }

	    @Override
	    public long getTotalPostalVotes() {

	        return candidates.stream()
	                .mapToLong(CandidateResult::getPostalVotes)
	                .sum();
	    }

	    @Override
	    public long getTotalCandidates() {

	        return candidates.size();
	    }

	    @Override
	    public Map<String, Long> getCandidateCountByParty() {

	        return candidates.stream()
	                .collect(Collectors.groupingBy(
	                        CandidateResult::getParty,
	                        Collectors.counting()));
	    }

	    @Override
	    public Map<String, Long> getCandidateCountByState() {

	        return candidates.stream()
	                .collect(Collectors.groupingBy(
	                        CandidateResult::getState,
	                        Collectors.counting()));
	    }
	    
	    
	    
	    
}
