package com.election.task;

import java.util.List;

import com.election.task.model.CandidateResult;
import com.election.task.model.ElectionResult;
import com.election.task.service.ElectionService;
import com.election.task.util.ResultDataReader;

public class Main {

	public static void main(String[] args) {

		
		 ResultDataReader reader = new ResultDataReader();

	        List<ElectionResult> winners =
	                reader.readElectionResult("2019_Results_Winning_Candidate.csv");

	        List<CandidateResult> candidates =
	                reader.readCandidateResult("2019_Results.csv");

	        ElectionService service = new ElectionService();

	        System.out.println("--------------- TASK 1 ----------------");
	        service.task1(winners, candidates);

	        System.out.println("\n--------------- TASK 2 ----------------");
//	        service.task2(candidates);

	        System.out.println("\n--------------- TASK 3 ----------------");
//	        service.task3(winners, candidates);

	        System.out.println("\n--------------- TASK 4 ----------------");
//	        service.task4(winners);
		
	}

}
