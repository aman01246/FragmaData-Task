package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.dto.ConstituencySummary;
import com.task.dto.PartySummary;
import com.task.dto.StateSummary;
import com.task.model.CandidateResult;
import com.task.model.ElectionResult;

public interface ElectionServiceImpl {

    // Load all data
    List<ElectionResult> getAllElectionResults();

    List<CandidateResult> getAllCandidateResults();

    // Basic Tasks
    Map<String, PartySummary> getPartySummary();

    List<StateSummary> getStateSummary();

    List<ConstituencySummary> getConstituencySummary();

    List<ElectionResult> getAbsoluteMajorityWinners();

//    // Party Analysis
//    Map<String, Long> getPartyWiseSeats();
//
//    Map<String, Long> getPartyWiseVotes();
//
//    Map<String, Double> getPartyVotePercentage();
//
//    // State Analysis
//    Map<String, Long> getStateWiseSeats();
//
//    Map<String, Long> getStateWiseVotes();
//
//    // Constituency Analysis
//    List<CandidateResult> getCandidatesByConstituency(String constituency);
//
//    List<CandidateResult> getCandidatesByState(String state);
//
//    CandidateResult getRunnerUp(String constituency);
//
//    // Ranking
//    List<ElectionResult> getTop10HighestVoteShare();
//
//    List<ElectionResult> getTop10LowestVoteShare();
//
//    List<CandidateResult> getTop10MostVotes();
//
//    // Search APIs
//    ElectionResult getWinnerByConstituency(String constituency);
//
//    ElectionResult getWinnerByCandidate(String candidate);
//
//    // Statistics
//    long getTotalVotes();
//
//    long getTotalSeats();
//
//    long getTotalCandidates();
    
}
