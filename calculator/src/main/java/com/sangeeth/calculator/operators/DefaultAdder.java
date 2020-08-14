package com.sangeeth.calculator.operators;
import java.util.List;

public class DefaultAdder implements Adder{

	@Override
	public int add(List<Integer> numbers) {
		if(numbers.size() == 0 || numbers==null)
			throw new IllegalArgumentException("List is Empty");
		else 
			return numbers.stream().mapToInt(i -> i).sum();
	}
	
}
