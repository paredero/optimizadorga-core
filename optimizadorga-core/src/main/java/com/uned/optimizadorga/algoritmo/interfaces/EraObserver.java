package com.uned.optimizadorga.algoritmo.interfaces;

import com.uned.optimizadorga.algorithm.Generation;

/**
 * Interfaz para implementar el patron observer en la relaci�n entre algoritmo y
 * era El algoritmo crea una era que se ejecuta y va actualizando al algoritmo
 * segun va cambiando su estado Las modificaciones del estado de una era
 * consisten en que se calcule una nueva generaci�n
 * 
 * @author Francisco Javier Garc�a Paredero
 * 
 */
public interface EraObserver {

	/**
	 * Cuando la era finaliza el calculo de una generaci�n pasa los resultados
	 * al algoritmo para que muestre los resultados parciales
	 * 
	 * @param generacionProcesada
	 */
	public void updateGeneracion(Generation generacionProcesada);
}
