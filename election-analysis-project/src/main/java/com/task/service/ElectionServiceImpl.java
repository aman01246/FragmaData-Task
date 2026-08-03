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

    // Tasks
    Map<String, PartySummary> task1();

    List<StateSummary> task2();

    List<ConstituencySummary> task3();

    List<ElectionResult> task4();

    // Extra Task
    List<CandidateResult> top5Candidates();
    
    Map<String, Long> top5Party();
    
    Map<String, Long> constituencyWithMaxCandidates();
    
    Map<String, Long> maxStateVotes();
    
    String findCandidates(String constituency);
    
    
    
}
