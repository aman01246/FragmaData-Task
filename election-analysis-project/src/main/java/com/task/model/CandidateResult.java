package com.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResult {

	private String state;
	private String constituency;
	private int serialNo;
	private String candidate;
	private String party;
	private long evmVotes;
	private long postalVotes;
	private long totalVotes;

}
