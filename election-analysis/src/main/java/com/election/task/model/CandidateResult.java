package com.election.task.model;

public class CandidateResult {

	private String state;
	private String constituency;
	private int serialNo;
	private String candidate;
	private String party;
	private long evmVotes;
	private long postalVotes;
	private long totalVotes;

	public CandidateResult() {
		super(); 
	}

	public CandidateResult(String state, String constituency, int serialNo, String candidate, String party,
			long evmVotes, long postalVotes, long totalVotes) {
		super();
		this.state = state;
		this.constituency = constituency;
		this.serialNo = serialNo;
		this.candidate = candidate;
		this.party = party;
		this.evmVotes = evmVotes;
		this.postalVotes = postalVotes;
		this.totalVotes = totalVotes;
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

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public long getEvmVotes() {
		return evmVotes;
	}

	public void setEvmVotes(long evmVotes) {
		this.evmVotes = evmVotes;
	}

	public long getPostalVotes() {
		return postalVotes;
	}

	public void setPostalVotes(long postalVotes) {
		this.postalVotes = postalVotes;
	}

	public long getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(long totalVotes) {
		this.totalVotes = totalVotes;
	}

	@Override
	public String toString() {
		return "CandidateResult [state=" + state + ", constituency=" + constituency + ", serialNo=" + serialNo
				+ ", candidate=" + candidate + ", party=" + party + ", evmVotes=" + evmVotes + ", postalVotes="
				+ postalVotes + ", totalVotes=" + totalVotes + "]";
	}

}
