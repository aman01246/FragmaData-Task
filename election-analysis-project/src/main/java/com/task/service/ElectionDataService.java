package com.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.task.model.CandidateResult;
import com.task.model.ElectionResult;
import com.task.util.DataReader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ElectionDataService {

	private final DataReader dataReader;

    private List<ElectionResult> winners;
    private List<CandidateResult> candidates;

    @PostConstruct
    public void loadData() {
        winners = dataReader.readElectionResult("2019_Results_Winning_Candidate.csv");
        candidates = dataReader.readCandidateResult("2019_Results.csv");
    }

    public List<ElectionResult> getWinners() {
        return winners;
    }

    public List<CandidateResult> getCandidates() {
        return candidates;
    }
}
