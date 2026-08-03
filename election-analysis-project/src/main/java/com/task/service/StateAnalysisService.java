package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.model.CandidateResult;
import com.task.model.ElectionResult;

public interface StateAnalysisService {

	// Get all states
	List<String> getAllStates();

	// Get all winners from a state
	List<ElectionResult> getWinningCandidatesByState(String state);

	// Get all candidates from a state
	List<CandidateResult> getCandidatesByState(String state);

	// Total votes polled in a state
	long getTotalVotesByState(String state);

	// Total constituencies in a state
	long getTotalConstituenciesByState(String state);

	// Total candidates in a state
	long getTotalCandidatesByState(String state);

	// Party-wise seats in a state
	Map<String, Long> getPartyWiseSeatsByState(String state);

	// Party-wise votes in a state
	Map<String, Long> getPartyWiseVotesByState(String state);

	// Winners with more than 50% vote share in a state
	List<ElectionResult> getAbsoluteMajorityWinnersByState(String state);

}
