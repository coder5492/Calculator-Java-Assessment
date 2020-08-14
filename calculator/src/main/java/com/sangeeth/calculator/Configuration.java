package com.sangeeth.calculator;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Configuration {
	public String resultsFileHeading;
	public String emailSubject;
	public String emailText;
	public String emailAddress;
	public String password;
	public String host;
	public String port;
	private final String configFileLocation = "src/main/resources/config.yml";
	static Logger log=Logger.getLogger(CalculatorApplication.class.getName());

	public void getConfiguration() throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			log.info("Getting Configuration config,.yml");
			Configuration config = mapper.readValue(new File(this.configFileLocation), Configuration.class);
			setConfiguration(config);
		} catch (Exception e) {
			log.debug("Couldn't read configurations from config.yml.");
			log.error(e.getMessage());
			e.printStackTrace();
			
			throw new IOException("Couldn't read configurations from config.yml. "
					+ "Check if smtp settings are configured at config.yml file.");
		} 
	}
	
	private void setConfiguration(Configuration config) { 
		this.emailSubject = config.emailSubject;
		this.emailText    = config.emailText;
		this.emailAddress = config.emailAddress;
		this.password	  = config.password;
		this.port         = config.port;
		this.host     	  = config.host;
		this.resultsFileHeading  = config.resultsFileHeading;
	}
	
	public String getEmailSubject() {
		return this.emailSubject;
	}
	
	public String getEmailText() {
		return this.emailText;
	}
	
	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public String getPort() {
		return this.host;
	}
	
	public String resultsFileHeading() {
		return this.resultsFileHeading;
	}
}
