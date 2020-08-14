# Calculator-Java-Assessment

1. Please ensure that you update src/main/resoruces/config.yml with your gmail emailId and password for the emailSending fucntionality to work.
2. The main Class for the application is named as CalculatorApplication.java at sr/main/java/com/sangeeth/calculator/
3. To use it as a librarary create a runnable jar file and add it to your project. Then you can import it to your Class.
4. Sample code on how to use the library

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
