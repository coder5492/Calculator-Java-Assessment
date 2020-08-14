package com.sangeeth.calculator;

public class ResultsFile {
	public String fileName;
	public FileFormat fileFormat;
	public String fileHeading;
	
	public ResultsFile(String fileName, FileFormat fileFormat) {
		this.fileName = fileName + "." + fileFormat;
		this.fileFormat = fileFormat;
	}
}
