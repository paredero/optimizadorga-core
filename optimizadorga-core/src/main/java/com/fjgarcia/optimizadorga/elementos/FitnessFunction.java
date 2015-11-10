/**
 * 
 */
package com.fjgarcia.optimizadorga.elementos;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

/**
 * Representa la funcion que se quiere optimizar
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class FitnessFunction {
	private static final String VARIABLE_NO_ASIGNADA1 = "The setVariable ";
	private static final String VARIABLE_NO_ASIGNADA2 = "has not been set";
//	private String expresion;
	private Expression expresion;
	private String strExpr;
	public FitnessFunction(String strExpr, Map<String, GeneType> parametros) throws Exception {
		super();
		this.validarExpresion(strExpr);
		this.strExpr = strExpr;
		ExpressionBuilder eb = new ExpressionBuilder(strExpr);
		eb.variables("pi");
		eb.variables("e");
		
		for (String var:parametros.keySet()) {
			eb.variables(var.toLowerCase());
		}
		
		expresion = eb.build();
		expresion.setVariable("pi", Math.PI);
		expresion.setVariable("e", Math.E);
		
		ValidationResult resultadoValidacion = expresion.validate();
		if (!resultadoValidacion.isValid()) {
			Iterator<String> itErrores = resultadoValidacion.getErrors().iterator();
			while (itErrores.hasNext()) {
				String error = itErrores.next();
				if (!error.contains(VARIABLE_NO_ASIGNADA1) 
						&& !error.contains(VARIABLE_NO_ASIGNADA2)) {
					// El error variable no asignada es aceptable, pues solo asignare al evaluar
					throw new Exception(error);
				}
			}
		}
	}


	/**
	 * Valida inicialmente la expresion
	 * @param strExpr
	 * @throws Exception 
	 */
	private void validarExpresion(String strExpr) throws Exception {
		// 1 Valida los parentesis
		Stack<Character> stack = new Stack<Character>();
		char c;
		for (int i = 0; i<strExpr.length(); i++) {
			c = strExpr.charAt(i);
			if(c == '(') {
	            stack.push(c);
			} else if(c == ')') {
	            if(stack.empty()) {
	            	throw new Exception("parentesis no abierto");
	            } else if(stack.peek() == '('){
	            	stack.pop();
	            }
			}
		}

		if (!stack.empty()) {
			throw new Exception("parentesis no cerrado");
		}
	}


/**
 * Evalua la funcion para una serie de parametros
 * @param listaParametros
 * @return el valor de la funci�n ejecutada con los parametros pasados
 * @throws Exception
 */
	public double evaluate(List<Gene> listaParametros) throws Exception{		
		for (Gene parametro:listaParametros) {
			expresion.setVariable(parametro.getGeneType().getName().toLowerCase(), parametro.getValue());
		}
		return expresion.evaluate();
	}


	/**
	 * @return the expresion
	 */
	public String getStrExpresion() {
		return this.strExpr;
	}



	
}
