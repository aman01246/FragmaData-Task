package com.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResult {

	private String state;
	private String constituency;
	private long votes;
	private double percentage;
	private String party;
	private String candidate;	
}
