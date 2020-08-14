package com.sangeeth.calculator.operators;
import java.util.List;

public class DefaultMultiplier implements Multiplier {

	@Override
	public int multiply(List<Integer> numbers) {
		if(numbers.size() == 0 || numbers==null)
			throw new IllegalArgumentException("List is Empty");
		else
			return numbers.stream().reduce(1, (a, b) -> a*b);
	}

}
