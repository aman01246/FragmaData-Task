package com.task.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task.dto.PartySummary;
import com.task.model.ElectionResult;
import com.task.util.DataReader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class PartyAnalysisServiceImpl implements PartyAnalysisService {

	 private final DataReader dataReader;

	    private List<ElectionResult> winners;

	    @PostConstruct
	    public void loadData() {
	        winners = dataReader.readElectionResult("2019_Results.csv");
	    }

	    @Override
	    public Map<String, Long> getPartyWiseSeats() {

	        return winners.stream()
	                .filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
	                .collect(Collectors.groupingBy(
	                        ElectionResult::getParty,
	                        Collectors.counting()));
	    }

	    @Override
	    public Map<String, Long> getPartyWiseVotes() {

	        return winners.stream()
	                .filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
	                .collect(Collectors.groupingBy(
	                        ElectionResult::getParty,
	                        Collectors.summingLong(ElectionResult::getVotes)));
	    }
	    
	    @Override
	    public Map<String, Double> getPartyVotePercentage() {

	        Map<String, Long> partyVotes = getPartyWiseVotes();

	        long totalVotes = partyVotes.values()
	                .stream()
	                .mapToLong(Long::longValue)
	                .sum();

	        return partyVotes.entrySet()
	                .stream()
	                .collect(Collectors.toMap(
	                        Map.Entry::getKey,
	                        e -> (e.getValue() * 100.0) / totalVotes
	                ));
	    }

	    @Override
	    public List<ElectionResult> getWinnersByParty(String party) {

	        return winners.stream()
	                .filter(e -> e.getParty().equalsIgnoreCase(party))
	                .toList();
	    }

	    @Override
	    public long getTotalParties() {

	        return winners.stream()
	                .map(ElectionResult::getParty)
	                .distinct()
	                .count();
	    }

	    @Override
	    public String getPartyWithMaximumSeats() {

	        return getPartyWiseSeats()
	                .entrySet()
	                .stream()
	                .max(Map.Entry.comparingByValue())
	                .map(Map.Entry::getKey)
	                .orElse("No Data");
	    }
	    
	    @Override
	    public String getPartyWithHighestVoteShare() {

	        return getPartyVotePercentage()
	                .entrySet()
	                .stream()
	                .max(Map.Entry.comparingByValue())
	                .map(Map.Entry::getKey)
	                .orElse("No Data");
	    }

	    @Override
	    public List<PartySummary> getTopPartiesBySeats(int limit) {

	        Map<String, Long> seats = getPartyWiseSeats();
	        Map<String, Long> votes = getPartyWiseVotes();
	        Map<String, Double> percentages = getPartyVotePercentage();

	        return seats.entrySet()
	                .stream()
	                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
	                .limit(limit)
	                .map(e -> new PartySummary(
	                			e.getKey(),
	                        votes.get(e.getKey()),
	                        seats.get(e.getKey()),
	                        percentages.get(e.getKey())
	                ))
	                .toList();
	    }

	    @Override
	    public List<PartySummary> getTopPartiesByVotes(int limit) {

	        Map<String, Long> seats = getPartyWiseSeats();
	        Map<String, Long> votes = getPartyWiseVotes();
	        Map<String, Double> percentages = getPartyVotePercentage();

	        return votes.entrySet()
	                .stream()
	                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
	                .limit(limit)
	                .map(e -> new PartySummary(
	                			e.getKey(),
	                			votes.get(e.getKey()),
	                        seats.get(e.getKey()),
	                        percentages.get(e.getKey())
	                ))
	                .toList();
	    }
	    
}
