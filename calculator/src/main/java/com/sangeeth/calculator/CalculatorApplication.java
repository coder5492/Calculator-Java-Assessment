package com.sangeeth.calculator;

import com.sangeeth.calculator.operators.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

class CustomAdder implements Adder{
	public int add(List<Integer> numbers)
	{
		return numbers.size();
	}
}
class CustomSubtractor implements Subtractor{
	public int subtract(int a, int b)
	{
		return a*b;
	}
}
class CustomMultiplier implements Multiplier{
	public int multiply(List<Integer> numbers)
	{
		return numbers.size();
	}
}
class CustomDivider implements Divider{
	public int divide(int a, int b)
	{
		return a+5+b;
	}
}
public class CalculatorApplication {

	public static void main(String[] args) throws IOException, MessagingException {
		LoggingStratergy myLoggingStratergy = new LoggingStratergy("sangeethkichu100@gmail.com",FileFormat.txt);
		CustomAdder cA = new CustomAdder();
		CustomMultiplier cM = new CustomMultiplier();
		CustomDivider cD = new CustomDivider();
		CustomSubtractor cS = new CustomSubtractor();
		
		Calculator customCalculator = new Calculator.builder()
									.adder(cA)
									.subtractor(cS)
									.divider(cD)
									.multiplier(cM)
									.loggingStratergy(myLoggingStratergy)
									.build();
		 
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(1); temp.add(3); temp.add(4);
		String expression = "5 + 9 * 7 - 50 / 5";

		System.out.println(customCalculator.calculateExpression(expression));
		System.out.println(customCalculator.add(temp));
		System.out.println(customCalculator.subtract(5, 8));
		System.out.println(customCalculator.multiply(temp));
		System.out.println(customCalculator.divide(10, 3));
		customCalculator.sendResultsAsEmail();

	}
}

