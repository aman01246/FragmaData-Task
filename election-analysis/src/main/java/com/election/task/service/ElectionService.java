package com.election.task.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.election.task.model.CandidateResult;
import com.election.task.model.ElectionResult;

public class ElectionService {

	public void task1(List<ElectionResult> winners, List<CandidateResult> candidates) {

		// totalVotes
		Map<String, Long> partyVotes = winners.stream()
				.filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
				.collect(Collectors.groupingBy(e -> e.getParty(), 
						 Collectors.summingLong(ElectionResult::getVotes)));

		partyVotes.forEach((k,v)->System.out.printf("%-45s %-15s \n",k,v));

		// seats
		Map<String, Long> seatsWon = winners.stream()
				.filter(e -> !e.getParty().equalsIgnoreCase("Independent"))
				.collect(Collectors.groupingBy(e -> e.getParty(),
						 Collectors.counting()));

		seatsWon.forEach((k,v)->System.out.printf("%-45s %-15s \n",k,v));
		
		long totalVotes = partyVotes.values().stream()
				.mapToLong(Long::longValue)
				.sum();

		// Print Result
		System.out.printf("%-45s %-15s %-10s %-10s%n", "Party", "Total Votes", "Seats Won", "Vote %");
//		System.out.println("Party \t  Total Votes \t Seats Won \t Vote %");
		System.out.println("------------------------------------------------------------------------");

		for (String party : partyVotes.keySet()) {

			long votes = partyVotes.get(party); 
			long seats = seatsWon.getOrDefault(party, 0L);

			double percentage = (votes * 100.0) / totalVotes;

			System.out.printf("%-45s %-15d %-10d %.2f%%%n", party, votes, seats, percentage);
			
		}
	}

	public void task2(List<CandidateResult> candidates) {

		// grouping by states
		Map<String, List<CandidateResult>> stateMap = candidates.stream()
				.collect(Collectors.groupingBy(e -> e.getState()));

		System.out.printf("%-35s %-20s %-20s%n", "State", "Constituencies", "Total Votes");
		System.out.println("-------------------------------------------------------------------");

		for (String state : stateMap.keySet()) {

			List<CandidateResult> list = stateMap.get(state);

			//based on states find the constituency
			long constituencyCount = list.stream().map(e -> e.getConstituency()).distinct().count();

			//based on states find the total votes
			long totalVotes = list.stream().mapToLong(e -> e.getTotalVotes()).sum();

			System.out.printf("%-35s %-20d %-20d%n", state, constituencyCount, totalVotes);
		}

	}

	public void task3(List<ElectionResult> winners, List<CandidateResult> candidates) {

		Map<String, List<CandidateResult>> constituencyMap = candidates.stream()
				.collect(Collectors.groupingBy(CandidateResult::getConstituency));

		System.out.printf("%-30s %-30s %-35s %-15s %-15s %-15s%n", "Constituency", "Winner", "Party", "Winner Votes",
				"Total Votes", "Candidates");
		System.out.println("----------------------------------------------------------------------------------------");
		for (ElectionResult winner : winners) {

			List<CandidateResult> list = constituencyMap.get(winner.getConstituency());

			long totalVotes = list.stream().mapToLong(CandidateResult::getTotalVotes).sum();

			long totalCandidates = list.size();

			System.out.printf("%-30s %-30s %-35s %-15d %-15d %-15d%n", winner.getConstituency(), winner.getCandidate(),
					winner.getParty(), winner.getVotes(), totalVotes, totalCandidates);
		}

	}

	public void task4(List<ElectionResult> winners, List<CandidateResult> candidates) {


		System.out.printf("%-30s %-30s %-15s%n", "Constituency", "Winning Candidate", "Vote Share");
		System.out.println("---------------------------------------------------------------------");
		
		for (ElectionResult winner : winners) {

			if (winner.getPercentage() > 50) {

				System.out.printf("%-30s %-30s %.2f%%%n", winner.getConstituency(), winner.getCandidate(), winner.getPercentage());
			}

		}
	}

	

}
