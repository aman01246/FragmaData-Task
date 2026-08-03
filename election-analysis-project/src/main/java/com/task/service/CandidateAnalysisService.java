package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.model.CandidateResult;

public interface CandidateAnalysisService {

	 // Fetch all candidates
    List<CandidateResult> getAllCandidates();
    
 // Search candidate
    CandidateResult getCandidateByName(String candidateName);
    
 // Filter candidates
    List<CandidateResult> getCandidatesByState(String state);
    
    List<CandidateResult> getCandidatesByConstituency(String constituency);
    
    List<CandidateResult> getCandidatesByParty(String party);
    

    // Vote Analysis
    CandidateResult getCandidateWithHighestVotes();

    CandidateResult getCandidateWithLowestVotes();

    List<CandidateResult> getTopCandidatesByVotes(int limit);

    List<CandidateResult> getBottomCandidatesByVotes(int limit);
    
    // Vote Statistics
    long getTotalVotes();

    long getTotalEvmVotes();

    long getTotalPostalVotes();

    // Candidate Statistics
    long getTotalCandidates();

    Map<String, Long> getCandidateCountByParty();

    Map<String, Long> getCandidateCountByState();
    
}
