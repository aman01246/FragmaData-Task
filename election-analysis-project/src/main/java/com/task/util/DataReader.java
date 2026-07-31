package com.task.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.task.model.CandidateResult;
import com.task.model.ElectionResult;

@Component
public class DataReader {

	public List<ElectionResult> readElectionResult(String filePath) {

		List<ElectionResult> list = new ArrayList<>();
		
		try {
		
		CSVParser parser = getParser(filePath);

		for(CSVRecord record : parser) {
			
			ElectionResult result = new ElectionResult();
			
			result.setState(record.get(0));
			result.setConstituency(record.get(1));
			result.setVotes(Long.parseLong(record.get(2)));
			result.setPercentage(Double.parseDouble(record.get(3)));
			result.setParty(record.get(4));
			result.setCandidate(record.get(5));
			
			list.add(result);
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;

	}

	public List<CandidateResult> readCandidateResult(String fileName)  {

		List<CandidateResult> list = new ArrayList<>();

		try {
			
		CSVParser parser = getParser(fileName);

		 for(CSVRecord record : parser) {
			
			CandidateResult result = new CandidateResult();

			result.setState(record.get(0));
			result.setConstituency(record.get(1));
			result.setSerialNo(Integer.parseInt(record.get(2)));
			result.setCandidate(record.get(3));
			result.setParty(record.get(4));
			result.setEvmVotes(Long.parseLong(record.get(5)));
			result.setPostalVotes(Long.parseLong(record.get(6)));
			result.setTotalVotes(Long.parseLong(record.get(7)));

			list.add(result);
		 }

		}catch (Exception e) {
			e.printStackTrace();
		 }

		return list;

	}
	

	@SuppressWarnings("deprecation")
	private CSVParser getParser(String fileName) throws IOException{
		
		InputStream input = getClass()
				.getClassLoader()
				.getResourceAsStream(fileName);
		
		if (input == null) {
		    throw new IOException("File not found: " + fileName);
		}
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		
		CSVParser parser = CSVFormat.DEFAULT
				.builder()
				.setHeader()
				.setSkipHeaderRecord(true)
				.build()
				.parse(reader);
		
		return parser;
	}
	
}
