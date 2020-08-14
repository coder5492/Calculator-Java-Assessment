package com.sangeeth.calculator;

import com.sangeeth.calculator.operators.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

public class ExpressionParser {
	private String expression;
	private Adder adder;
	private Subtractor subtractor;
	private Multiplier multiplier;
	private Divider divider;
	static Logger log=Logger.getLogger(CalculatorApplication.class.getName());

	private ExpressionParser(Builder builder) {
		this.expression = builder.expression;
		this.adder 		= builder.adder;
		this.subtractor = builder.subtractor;
		this.multiplier = builder.multiplier;
		this.divider    = builder.divider;
	}
	
	public static class Builder {
			
			public Adder adder;
			public String expression; 
			public Subtractor subtractor;
			public Multiplier multiplier;
			public Divider divider;
			
			public Builder(String expression) {
				this.expression =  expression;
			}
			
			public Builder adder(Adder adder) {
				this.adder = adder;
				return this;
			}
			public Builder subtractor(Subtractor subtractor) {
				this.subtractor = subtractor;
				return this;
			}
			public Builder divider(Divider divider) {
				this.divider = divider;
				return this;
			}
			public Builder multiplier(Multiplier multiplier) {
				this.multiplier = multiplier;
				return this;
			}
			
			public ExpressionParser build() {
				return new ExpressionParser(this);
			}
	}
	
	

	 public int evaluate() { 
		log.info("Parsing expression");
        char[] characters = this.expression.toCharArray(); 
  
        Stack<Integer> numbers = new Stack<Integer>(); 
        Stack<Character> operators = new Stack<Character>(); 
  
        for (int i = 0; i < characters.length; i++) 
        { 
            if (this.isWhiteSpace(characters[i])) 
                continue; 
            
            if (this.isaDigit(characters[i])) { 
                StringBuffer stringBuffer = new StringBuffer(); 
                while (i < characters.length && this.isaDigit(characters[i])) 
                    stringBuffer.append(characters[i++]); 
                numbers.push(Integer.parseInt(stringBuffer.toString())); 
            } 
  
            else if (characters[i] == '(') 
                operators.push(characters[i]); 
  
            else if (characters[i] == ')') { 
                while (operators.peek() != '(') 
                  numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop())); 
                operators.pop(); 
            } 
  
            else if (this.isAnOperator(characters[i])) 
            { 
                while (!operators.empty() && hasPrecedence(characters[i], operators.peek())) 
                  numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop())); 
  
                operators.push(characters[i]); 
            } 
        } 
  
        while (!operators.empty()) 
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop())); 
  
        return numbers.pop(); 
    }

	 private boolean hasPrecedence(char op1, char op2) { 
        if (op2 == '(' || op2 == ')') 
            return false; 
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
            return false; 
        else
            return true; 
     } 
	    
     private List<Integer> makeList(int a, int b) {
    	List<Integer> temporaryList = new ArrayList<Integer>();
    	temporaryList.add(a);
    	temporaryList.add(b);
    	return temporaryList;
     }
	  
    private int applyOperator(char op, int b, int a) {
        switch (op) 
        { 
	        case '+': 
	            return this.adder.add(makeList(a, b)); 
	        case '-': 
	            return this.subtractor.subtract(a, b); 
	        case '*': 
	            return this.multiplier.multiply(makeList(a,b)); 
	        case '/': 
	            return this.divider.divide(a, b); 
	        default: 
	        	log.error("Illegal argument used in expression");
	        	throw new IllegalArgumentException("Operator "+ op + " is not valid");
        } 
    } 
    
    private boolean isWhiteSpace(char ch) {
    	return ch == ' ';
    }
    
    private boolean isaDigit(char ch) {
    	return (ch >= '0' && ch <= '9');
    }
    
    private boolean isAnOperator(char ch) {
    	return (ch == '+' || ch == '-' || 
                ch == '*' || ch == '/');
    }
}
