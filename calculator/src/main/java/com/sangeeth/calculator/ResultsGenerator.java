package com.sangeeth.calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

public class ResultsGenerator {
	public ResultsFile resultsFile;
	private List<String> calculations;
	static Logger log=Logger.getLogger(CalculatorApplication.class.getName());

	public ResultsGenerator(List<String> calculations, ResultsFile resultsFile) throws IOException {
		this.resultsFile = resultsFile;
		this.calculations = calculations;
		this.resultsFile.fileHeading = this.GetFileHeading();
	}
	
	public void generateResults() throws IOException {
		log.info("Generating Results");
		this.createFile();
		this.writeResultsToFile(this.calculations);
	}
	
	private void createFile() {	
		File obj = new File(this.resultsFile.fileName);
		try {
			if(obj.createNewFile())
				log.info("Results file created");
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug("Couldn't create file");
			e.printStackTrace();
		}
	}
	
	private void writeResultsToFile(List<String> calculations) throws IOException {
		log.info("Writing to file");
		this.setFileHeading();
		this.setFileContent(calculations);
	}
	
	private void setFileHeading() throws IOException {
		FileWriter writer = new FileWriter(this.resultsFile.fileName);
		writer.write(this.resultsFile.fileHeading);
		writer.write("\n");
		writer.close();
	}
	
	private void setFileContent(List<String> calculations) throws IOException {
		FileWriter writer = new FileWriter(this.resultsFile.fileName, true); 
		try {
			calculations.forEach(calculation -> {
				try {
					writer.write('\n');
					writer.write(calculation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			writer.close();
			log.info("Writing Success");
		} catch(IOException e) {
			log.error(e.getMessage());
			throw new IOException("Unable to write calculations to file");
		}
		
	}
	
	private String GetFileHeading() throws IOException {
		Configuration config = new Configuration();
		config.getConfiguration();
		return config.resultsFileHeading();
	}
}
