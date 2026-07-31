package com.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartySummary {

    private long totalVotes;

    private long seatsWon;

    private double votePercentage;

}