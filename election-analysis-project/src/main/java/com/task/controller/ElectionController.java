package com.task.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.ApiResponse;
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

    // All Winning Candidates Result
    @Operation(
            summary = "Get all election winners",
            description = "Returns the list of winning candidates for all constituencies."
    )
    @GetMapping("/results")
    public ResponseEntity<ApiResponse<List<ElectionResult>>> getResults() {

        return ResponseEntity.ok(
                ApiResponse.<List<ElectionResult>>builder()
                        .success(true)
                        .message("Election results fetched successfully.")
                        .data(service.getAllElectionResults())
                        .build()
        );
    }

    // All Candidates Participate in Election
    @Operation(
            summary = "Get all candidates",
            description = "Returns every candidate who contested the election."
    )
    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<List<CandidateResult>>> getCandidates() {

        return ResponseEntity.ok(
                ApiResponse.<List<CandidateResult>>builder()
                        .success(true)
                        .message("Candidate details fetched successfully.")
                        .data(service.getAllCandidateResults())
                        .build()
        );
    }

    // Task 1
    @Operation(
            summary = "Party-wise election summary",
            description = "Returns total votes, seats won and vote percentage for every party."
    )
    @GetMapping("/party-summary")
    public ResponseEntity<ApiResponse<Map<String, PartySummary>>> getPartySummary() {

        return ResponseEntity.ok(
                ApiResponse.<Map<String, PartySummary>>builder()
                        .success(true)
                        .message("Party summary fetched successfully.")
                        .data(service.task1())
                        .build()
        );
    }

    // Task 2
    @Operation(
            summary = "State-wise summary",
            description = "Returns constituency count and total votes for every state."
    )
    @GetMapping("/state-summary")
    public ResponseEntity<ApiResponse<List<StateSummary>>> getStateSummary() {

        return ResponseEntity.ok(
                ApiResponse.<List<StateSummary>>builder()
                        .success(true)
                        .message("State summary fetched successfully.")
                        .data(service.task2())
                        .build()
        );
    }

    // Task 3
    @Operation(
            summary = "Constituency-wise summary",
            description = "Returns winner, party, winner votes, total votes and number of candidates."
    )
    @GetMapping("/constituency-summary")
    public ResponseEntity<ApiResponse<List<ConstituencySummary>>> getConstituencySummary() {

        return ResponseEntity.ok(
                ApiResponse.<List<ConstituencySummary>>builder()
                        .success(true)
                        .message("Constituency summary fetched successfully.")
                        .data(service.task3())
                        .build()
        );
    }

    // Task 4
    @Operation(
            summary = "Absolute majority winners",
            description = "Returns winners with more than 50% vote share."
    )
    @GetMapping("/majority-winners")
    public ResponseEntity<ApiResponse<List<ElectionResult>>> getAbsoluteMajorityWinners() {

        return ResponseEntity.ok(
                ApiResponse.<List<ElectionResult>>builder()
                        .success(true)
                        .message("Absolute majority winners fetched successfully.")
                        .data(service.task4())
                        .build()
        );
    }
    
    //Extra 1
    @Operation(
            summary = "Top 5 candidates with maximum votes.",
            description = "Returns top 5 candidates with Maximum Votes."
    )
    @GetMapping("/top5Candidate")
    public ResponseEntity<ApiResponse<List<CandidateResult>>> getTop5Candidates(){
    		
    		return ResponseEntity.ok(
    				ApiResponse.<List<CandidateResult>>builder()
    				.success(true)
    				.message("Top 5 Candidates With Maximum Votes")
    				.data(service.top5Candidates())
    				.build()
    				);
    	
    }
    
    // Extra 2
    @Operation(
            summary = "Top 5 Party with maximum votes.",
            description = "Returns top 5 Party with Maximum Votes."
    )
    @GetMapping("/top5Party")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getTop5Party(){
    		
    		return ResponseEntity.ok(
    				ApiResponse.<Map<String, Long>>builder()
    				.success(true)
    				.message("Top 5 Party With Maximum Votes")
    				.data(service.top5Party())
    				.build()
    				);
    	
    }
    
    // Extra 3
    @Operation(
            summary = "Find constituency with maximum candidates.",
            description = "Returns constituency with maximum candidates."
    )
    @GetMapping("/maxCandidates")
    public ResponseEntity<ApiResponse<Map<String, Long>>> constituencyWithMaxCandidates(){
    		
    		return ResponseEntity.ok(
    				ApiResponse.<Map<String, Long>>builder()
    				.success(true)
    				.message(" constituency with maximum candidates")
    				.data(service.constituencyWithMaxCandidates())
    				.build()
    				);
    	
    }
    
    // Extra 4
    @Operation(
            summary = "Find state with highest turnout.",
            description = "Returns state with highest turnout/ Votes."
    )
    @GetMapping("/maxStateVote")
    public ResponseEntity<ApiResponse<Map<String, Long>>> maxStateVotes(){
    		
    		return ResponseEntity.ok(
    				ApiResponse.<Map<String, Long>>builder()
    				.success(true)
    				.message(" constituency with maximum candidates")
    				.data(service.maxStateVotes())
    				.build()
    				);
    	
    }
    
 // Extra 5
    @Operation(
            summary = "Search candidates by constituency name.",
            description = "Returns candidates by constituency name."
    )
    @GetMapping("/candidateByConstituency")
    public ResponseEntity<ApiResponse<String>> findCandidate(@RequestParam String constituency){
    		
    		return ResponseEntity.ok(
    				ApiResponse.<String>builder()
    				.success(true)
    				.message(" constituency with maximum candidates")
    				.data(service.findCandidates(constituency))
    				.build()
    				);
    	
    }
    
    
    

}