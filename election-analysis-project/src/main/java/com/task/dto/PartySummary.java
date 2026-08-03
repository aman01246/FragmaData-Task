package com.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartySummary {
	
	private String party;

    private long totalVotes;

    private long seatsWon;

    private double votePercentage;
    

}