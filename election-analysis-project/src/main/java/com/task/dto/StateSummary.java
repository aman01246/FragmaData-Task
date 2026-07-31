package com.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateSummary {

    private String state;

    private long constituencies;

    private long totalVotes;

}
