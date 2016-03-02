/**
 * 
 */
package com.uned.optimizadorga.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

/**
 * The fitness function 
 * @author Francisco Javier Garcia Paredero
 * TODO Might extract to interface so it could support more function types or implementation
 */
public class FitnessFunction {
	// For detecting exp4j exceptions
	private static final String NOT_ASSIGNED_VAR1 = "The setVariable ";
	private static final String NOT_ASSIGNED_VAR2 = "has not been set";
	
	private Expression expression;
	private String strExpr;
	
	/**
	 * Constructor
	 * @param strExpr the expression
	 * @param  the parameters of the function
	 * @throws Exception
	 */
	public FitnessFunction(String strExpr, Map<String, GeneType> parameters) throws Exception {
		super();
		this.expresionValidation(strExpr);
		this.strExpr = strExpr;
		ExpressionBuilder eb = new ExpressionBuilder(strExpr);
		eb.variables("pi");
		eb.variables("e");
		
		for (String var:parameters.keySet()) {
			eb.variables(var.toLowerCase());
		}
		
		expression = eb.build();
		expression.setVariable("pi", Math.PI);
		expression.setVariable("e", Math.E);
		
		ValidationResult validationResult = expression.validate();
		if (!validationResult.isValid()) {
			Iterator<String> itErrores = validationResult.getErrors().iterator();
			while (itErrores.hasNext()) {
				String error = itErrores.next();
				if (!error.contains(NOT_ASSIGNED_VAR1) 
						&& !error.contains(NOT_ASSIGNED_VAR2)) {
					// This error is acceptable because Im assigning vars on evaluation
					throw new Exception(error);
				}
			}
		}
	}


	/**
	 * Initial validation of the expression
	 * @param strExpr The expression as a string
	 * @throws Exception 
	 */
	private void expresionValidation(String strExpr) throws Exception {
		// Validates the parenthesis
		Stack<Character> stack = new Stack<Character>();
		char c;
		for (int i = 0; i<strExpr.length(); i++) {
			c = strExpr.charAt(i);
			if(c == '(') {
	            stack.push(c);
			} else if(c == ')') {
	            if(stack.empty()) {
	            	throw new Exception("Not opened parenthesis");
	            } else if(stack.peek() == '('){
	            	stack.pop();
	            }
			}
		}

		if (!stack.empty()) {
			throw new Exception("Non closed parenthesis");
		}
	}


	/**
	 * Performs evaluation after setting parameters. Should be synchronized
	 * since each evaluation should act with different parameter values and the
	 * program is working with a single instance of the Function
	 * 
	 * @param parameterList
	 * @return The value of the function runned with the parameters
	 * @throws Exception
	 */
	synchronized public double evaluate(List<Gene> parameterList) throws Exception{		
		for (Gene parameter:parameterList) {
			expression.setVariable(parameter.getGeneType().getName().toLowerCase(), 
					parameter.getValue());
		}
		return expression.evaluate();
	}


	/**
	 * @return the expresion
	 */
	public String getStrExpresion() {
		return this.strExpr;
	}
}
