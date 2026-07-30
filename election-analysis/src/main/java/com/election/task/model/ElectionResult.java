package com.election.task.model;

public class ElectionResult {

	private String state;
	private String constituency;
	private long votes;
	private double percentage;
	private String party;
	private String candidate;

	public ElectionResult() {
		super();
	}

	public ElectionResult(String state, String constituency, long votes, double percentage, String party,
			String candidate) {
		super();
		this.state = state;
		this.constituency = constituency;
		this.votes = votes;
		this.percentage = percentage;
		this.party = party;
		this.candidate = candidate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getConstituency() {
		return constituency;
	}

	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	@Override
	public String toString() {
		return "ElectionResult [state=" + state + ", constituency=" + constituency + ", votes=" + votes
				+ ", percentage=" + percentage + ", party=" + party + ", candidate=" + candidate + "]";
	}

}
