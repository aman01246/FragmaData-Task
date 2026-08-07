package com.task.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateWiseData {

	private String state;
	private List<String> constitutions;
	private List<String> candidates;
	private List<String> winCandidates;
	private Long totalVotes;
}
