package com.task.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task.dto.ConstituencySummary;
import com.task.dto.PartySummary;
import com.task.dto.StateSummary;
import com.task.exception.ResourceNotFoundException;
import com.task.model.CandidateResult;
import com.task.model.ElectionResult;
import com.task.util.DataReader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor				
public class ElectionService implements ElectionServiceImpl{

	  	private final DataReader dataReader;

	    private List<ElectionResult> winners;
	    private List<CandidateResult> candidates;
	
	    @PostConstruct
	    public void loadData() {
	    	winners = dataReader.readElectionResult("2019_Results_Winning_Candidate.csv");
	    	candidates = dataReader.readCandidateResult("2019_Results.csv");
	    }
	    
	
	@Override
	public List<ElectionResult> getAllElectionResults() {
		 if (winners.isEmpty()) {
		        throw new ResourceNotFoundException("Election results not found.");
		  }

		return winners;
	}

	@Override
	public List<CandidateResult> getAllCandidateResults() {
		  if (candidates.isEmpty()) {
		        throw new ResourceNotFoundException("Candidate results not found.");
		    }
		return candidates;
	}


	 // ================= Task 1 =================

    @Override
    public Map<String, PartySummary> task1() {

        Map<String, Long> partyVotes = winners.stream()
                .filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
                .collect(Collectors.groupingBy(ElectionResult::getParty,
                        Collectors.summingLong(ElectionResult::getVotes)));

        Map<String, Long> seatsWon = winners.stream()
                .filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
                .collect(Collectors.groupingBy(ElectionResult::getParty,
                        Collectors.counting()));

        long totalVotes = partyVotes.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        return partyVotes.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new PartySummary( 
                        			e.getKey(),
                                e.getValue(),
                                seatsWon.getOrDefault(e.getKey(), 0L),
                                (e.getValue() * 100.0) / totalVotes
                        )
                ));
    }

    // ================= Task 2 =================

    @Override
    public List<StateSummary> task2() {

        return candidates.stream()
                .collect(Collectors.groupingBy(CandidateResult::getState))
                .entrySet()
                .stream()
                .map(e -> new StateSummary(
                        e.getKey(),
                        e.getValue()
                                .stream()
                                .map(CandidateResult::getConstituency)
                                .distinct()
                                .count(),
                        e.getValue()
                                .stream()
                                .mapToLong(CandidateResult::getTotalVotes)
                                .sum()))
                .toList();

    }

    // ================= Task 3 =================

    @Override
    public List<ConstituencySummary> task3() {

        Map<String, List<CandidateResult>> constituencyMap = candidates.stream()
                .collect(Collectors.groupingBy(c ->
                        c.getConstituency() + "-" + c.getState()));

        return winners.stream()
                .map(winner -> {

                    List<CandidateResult> list =
                            constituencyMap.get(winner.getConstituency() + "-" + winner.getState());

                    long totalVotes = list.stream()
                            .mapToLong(CandidateResult::getTotalVotes)
                            .sum();

                    long totalCandidates = list.size();

                    return new ConstituencySummary(
                            winner.getConstituency(),
                            winner.getCandidate(),
                            winner.getParty(),
                            winner.getVotes(),
                            totalVotes,
                            totalCandidates);

                }).toList();

    }

    // ================= Task 4 =================

    @Override
    public List<ElectionResult> task4() {

        return winners.stream()
                .filter(w -> w.getPercentage() > 50)
                .toList();

    }

    //======================================
    
    
    // extra 1
	@Override
	public List<CandidateResult> top5Candidates() {
		
		return candidates.stream()
		.sorted((e1,e2)->Long.compare(e2.getTotalVotes(), e1.getTotalVotes()))
		.limit(5).toList();
		
	}

	 // extra 2
	@Override
	public Map<String, Long> top5Party() {
		
		
		return candidates.stream()
		.collect(Collectors.groupingBy(e->e.getParty(), 
				Collectors.summingLong(e->e.getTotalVotes())))
		.entrySet().stream()
		.sorted((e1,e2)->Long.compare(e2.getValue(), e1.getValue()))
		.limit(5)
		.collect(Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue,
				 (e1,e2)-> e1, 
				 LinkedHashMap::new ));
		
	}


	 // extra 3
	@Override
	public Map<String, Long> constituencyWithMaxCandidates() {
		
		 Map.Entry<String, Long> value = candidates.stream()
		            .collect(Collectors.groupingBy(
		                    CandidateResult::getConstituency,
		                    Collectors.counting()))
		            .entrySet()
		            .stream()
		            .max(Map.Entry.comparingByValue())
		            .orElse(null);

		    if (value == null) {
		        return Collections.emptyMap();
		    }

		    return Map.of(value.getKey(), value.getValue());
	}


	// extra 4
	@Override
	public Map<String, Long> maxStateVotes() {
		
		Entry<String, Long> value = candidates.stream()
		.collect(Collectors.groupingBy(e->e.getState(), Collectors.summingLong(e->e.getTotalVotes())))
		.entrySet().stream()
		.sorted((e1,e2)->Long.compare(e2.getValue(), e1.getValue()))
		.max(Map.Entry.comparingByValue())
		.orElse(null);
		
		if(value == null) {
			return Collections.emptyMap();
		}
		
		return Map.of(value.getKey(), value.getValue());
	}


	// extra 5
	@Override
	public String findCandidates(String constituency) {
		
		for(CandidateResult candidate: candidates) {
			if(candidate.getConstituency().equalsIgnoreCase(constituency)) {
				return candidate.getCandidate();
			}
		}
		return "Candidate not found";
	}


	
}
