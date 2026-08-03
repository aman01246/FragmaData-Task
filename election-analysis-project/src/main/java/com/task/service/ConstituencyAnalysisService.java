package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.model.CandidateResult;

public interface ConstituencyAnalysisService {

	// Get all candidates
    List<CandidateResult> getAllCandidates();
    
    // Get candidate by name
    CandidateResult getCandidateByName(String candidateName);
    
    // Get all candidates from a particular state
    List<CandidateResult> getCandidatesByState(String state);
	
 // Get all candidates from a constituency
    List<CandidateResult> getCandidatesByConstituency(String constituency);
    
 // Get all candidates belonging to a party
    List<CandidateResult> getCandidatesByParty(String party);
    
    // Candidate with highest total votes
    CandidateResult getCandidateWithHighestVotes();
    
 // Candidate with lowest total votes
    CandidateResult getCandidateWithLowestVotes();
    
    // Top N candidates by total votes
    List<CandidateResult> getTopCandidatesByVotes(int limit);
    
    // Bottom N candidates by total votes
    List<CandidateResult> getBottomCandidatesByVotes(int limit);
    
 // Total number of candidates
    long getTotalCandidates();
    
    // Total EVM votes
    long getTotalEvmVotes();

    // Total Postal votes
    long getTotalPostalVotes();

    // Total votes (EVM + Postal)
    long getTotalVotes();
    
 // Count candidates party-wise
    Map<String, Long> getCandidateCountByParty();

    // Count candidates state-wise
    Map<String, Long> getCandidateCountByState();
    
}
