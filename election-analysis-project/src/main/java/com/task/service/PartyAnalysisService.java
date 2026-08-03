package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.dto.PartySummary;
import com.task.model.ElectionResult;

public interface PartyAnalysisService {

	// Total seats won by each party
	Map<String, Long> getPartyWiseSeats();

	// Total votes received by each party
	Map<String, Long> getPartyWiseVotes();

	// Vote percentage of each party
	Map<String, Double> getPartyVotePercentage();

	// Winners of a particular party
	List<ElectionResult> getWinnersByParty(String party);

	// Total number of parties
	long getTotalParties();

	// Party with the maximum seats
	String getPartyWithMaximumSeats();

	// Party with the highest vote share
	String getPartyWithHighestVoteShare();

	// Top N parties by seats
	List<PartySummary> getTopPartiesBySeats(int limit);

	// Top N parties by votes
	List<PartySummary> getTopPartiesByVotes(int limit);

}
