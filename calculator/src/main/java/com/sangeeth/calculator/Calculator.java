package com.sangeeth.calculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.sangeeth.calculator.operators.Adder;
import com.sangeeth.calculator.operators.DefaultAdder;
import com.sangeeth.calculator.operators.DefaultDivider;
import com.sangeeth.calculator.operators.DefaultMultiplier;
import com.sangeeth.calculator.operators.DefaultSubtractor;
import com.sangeeth.calculator.operators.Divider;
import com.sangeeth.calculator.operators.Multiplier;
import com.sangeeth.calculator.operators.Subtractor;

public class Calculator {
	private Adder adder;
	private Subtractor subtractor;
	private Multiplier multiplier;
	private Divider divider;
	private LoggingStratergy loggingStratergy;
	private DefaultAdder defaultAdder = new DefaultAdder();
	private DefaultSubtractor defaultSubtractor = new DefaultSubtractor();
	private DefaultMultiplier defaultMultiplier = new DefaultMultiplier();
	private DefaultDivider defaultDivider = new DefaultDivider();
	private List<String> calculations = new ArrayList<String>();
	static Logger log=Logger.getLogger(CalculatorApplication.class.getName());

	private Calculator(builder builder){
		this.adder = ((builder.adder == null) ? this.defaultAdder : builder.adder);
		this.subtractor =(( builder.subtractor == null) ? this.defaultSubtractor : builder.subtractor);
		this.multiplier = ((builder.multiplier == null) ? this.defaultMultiplier : builder.multiplier);
		this.divider = ((builder.divider == null) ? this.defaultDivider : builder.divider);
		this.loggingStratergy = builder.loggingStratergy;
	}
	
	public static class builder {
		
		public Adder adder;
		public Subtractor subtractor;
		public Multiplier multiplier;
		public Divider divider;
		public LoggingStratergy loggingStratergy;
		
		public builder adder(Adder adder) {
			this.adder = adder;
			return this;
		}
		public builder subtractor(Subtractor subtractor) {
			this.subtractor = subtractor;
			return this;
		}
		public builder divider(Divider divider) {
			this.divider = divider;
			return this;
		}
		public builder multiplier(Multiplier multiplier) {
			this.multiplier = multiplier;
			return this;
		}

		public builder loggingStratergy(LoggingStratergy loggingStratergy) {
			this.loggingStratergy = loggingStratergy;
			return this;
		}
		public Calculator build() {
			log.info("Calculator created");
			return new Calculator(this);
		}
	}
	
	public int add(List<Integer> numbers) {
		if(numbers.size() == 0) 
		{
			log.error("List provided for adding is Empty");
			throw new IllegalArgumentException("List is Empty");
		}
		else {
			int answer = this.adder.add(numbers);
			this.addToCalculations(numbers, '+', answer);
			log.info("Numbers Successfully Added");
			return answer;
		}
	}
	
	public int subtract(int a, int b) {
		int answer = this.subtractor.subtract(a, b);
		this.addToCalculations(a, b, '-', answer);
		log.info("Numbers Successfully Subtracted");
		return answer;
	}
	
	public int multiply(List<Integer> numbers) {
		if(numbers.size() == 0) {
			log.error("List provided for adding is Empty");
			throw new IllegalArgumentException("List is Empty");
		}
		else {
			int answer = this.multiplier.multiply(numbers);
			this.addToCalculations(numbers, '*', answer);
			return answer;
	   }
	}
	
	public int divide(int a, int b) {
		int answer = this.divider.divide(a, b);
		this.addToCalculations(a, b, '/', answer);
		log.info("Numbers Successfully Subtracted");
		return answer;
	} 
	
	public int calculateExpression(String expression){
		ExpressionParser parser = new ExpressionParser.Builder(expression)
										.adder(this.adder)
										.subtractor(this.subtractor)
										.multiplier(this.multiplier)
										.divider(this.divider)
										.build();
		int answer = parser.evaluate();
		this.addToCalculations(expression, answer);
		log.info("Expression parsed and answer returned");
		return answer;
	}
	
	public void sendResultsAsEmail() throws IOException, MessagingException {
		if(isLoggingStratergy()) {
			ResultsFile resultFile = new ResultsFile("calculationResults", this.loggingStratergy.fileFormat);
			ResultsGenerator resultGenerator = new ResultsGenerator(this.calculations, resultFile );
			resultGenerator.generateResults();
			ResultsMailer resultsMailer = new ResultsMailer(resultFile);
			resultsMailer.mailResults(this.loggingStratergy.receiverEmail);
		}
		else {
			log.error("Logging Stratergy is Not set. Couldn't generate results");
			throw new IllegalArgumentException("Logging Stratergy is not Set. "
					+ "Use object.setLogginStratergy() to set LoggingStratergy.");
		}
	}
	
	public void setLoggingStratergy(LoggingStratergy loggingStratergy) {
		this.loggingStratergy = loggingStratergy;
	}
	
	private void addToCalculations(String expression, int answer) {
		String calculation = expression + " = " + Integer.toString(answer);
		this.calculations.add(calculation);
	}
	
	private void addToCalculations(int leftOperand, int rightOperand, char operator, int answer) {
		String calculation = Integer.toString(leftOperand) 
							+ " " + operator + " "
							+ Integer.toString(rightOperand)
							+ " = " + Integer.toString(answer);
		this.calculations.add(calculation);
	}
	
	private void addToCalculations(List<Integer> numbers, char operator, int answer) {
		String calculation = "";
		for(int i=0; i<numbers.size(); i++) {
			if(i+1 != numbers.size())
				calculation += Integer.toString(numbers.get(i)) + " " +  operator + " ";
		}
		calculation += Integer.toString(numbers.get(numbers.size()-1)) + " = " + Integer.toString(answer);
		this.calculations.add(calculation);
	}
	
	private boolean isLoggingStratergy() {
		return (this.loggingStratergy != null);
	}
}
