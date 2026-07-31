package com.task.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.ConstituencySummary;
import com.task.dto.PartySummary;
import com.task.dto.StateSummary;
import com.task.model.CandidateResult;
import com.task.model.ElectionResult;
import com.task.service.ElectionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/elections")
@Tag(
	    name = "Election Analysis API",
	    description = "APIs for analyzing election results from CSV files."
	)
public class ElectionController {

	private final ElectionService service;
	
    // Load all election results
	 @Operation(
		        summary = "Get all election winners",
		        description = "Returns the list of winning candidates for all constituencies."
		    )
	@GetMapping("/results")
	public List<ElectionResult> getResults() {
		return service.getAllElectionResults();
	}
	
	 // Load all candidate results
	 @Operation(
		        summary = "Get all candidates",
		        description = "Returns every candidate who contested the election along with their EVM votes, postal votes, and total votes."
		    )
	@GetMapping("/candidates")
	public List<CandidateResult> getCandidates() {
		return service.getAllCandidateResults();
	}
	
	// Task 1 - Party Summary
	 @Operation(
		        summary = "Party-wise election summary",
		        description = "Returns total votes, seats won, and vote percentage for each political party (excluding Independent candidates)."
		    )
    @GetMapping("/party-summary")
    public Map<String, PartySummary> getPartySummary() {
        return service.getPartySummary();
    }

    // Task 2 - State Summary
	 @Operation(
		        summary = "State-wise summary",
		        description = "Returns the number of constituencies and the total votes recorded in each state."
		    )
    @GetMapping("/state-summary")
    public List<StateSummary> getStateSummary() {
        return service.getStateSummary();
    }

    // Task 3 - Constituency Summary
	 @Operation(
		        summary = "Constituency-wise summary",
		        description = "Returns winner details, party, winner votes, total votes polled, and the number of candidates in each constituency."
		    )
    @GetMapping("/constituency-summary")
    public List<ConstituencySummary> getConstituencySummary() {
        return service.getConstituencySummary();
    }
    
    // Task 4 - Winners with more than 50% votes
	 @Operation(
		        summary = "Absolute majority winners",
		        description = "Returns all winning candidates who secured more than 50% of the total votes in their constituency."
		    )
    @GetMapping("/majority-winners")
    public List<ElectionResult> getAbsoluteMajorityWinners() {
        return service.getAbsoluteMajorityWinners();
    }
	
}
