package com.sangeeth.calculator.operators;

public class DefaultDivider implements Divider {

	public int divide(int a, int b) {
		if(b==0)
			throw new ArithmeticException("Division by 0 is not possible");
		else	
			return a/b;
	}

}
