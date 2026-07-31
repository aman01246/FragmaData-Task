package com.task.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstituencySummary {

    private String constituency;

    private String winner;

    private String party;

    private long winnerVotes;

    private long totalVotes;

    private long totalCandidates;

}