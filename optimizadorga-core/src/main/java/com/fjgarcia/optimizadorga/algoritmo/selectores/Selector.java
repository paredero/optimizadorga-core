package com.fjgarcia.optimizadorga.algoritmo.selectores;

import com.fjgarcia.optimizadorga.elementos.Poblacion;

/**
 * 
 * @author Francisco Javier Garc�a Paredero
 * Interfaz que deben cumplir los distintos operadores de selecci�n
 */
public interface Selector {

	String RULETA = "RULETA";
	String TORNEO = "TORNEO";

	/**
	 * Dada una poblaci�n original devuelve una nueva poblaci�n con los
	 * cromosomas seleccionados como resultado de aplicar el operador de
	 * seleccion
	 * 
	 * @param poblacion
	 * @return  una nueva poblaci�n con los
	 * cromosomas seleccionados como resultado de aplicar el operador de
	 * seleccion
	 */
	public Poblacion seleccionar(Poblacion poblacion);

	public String getTipoSelector();

}
