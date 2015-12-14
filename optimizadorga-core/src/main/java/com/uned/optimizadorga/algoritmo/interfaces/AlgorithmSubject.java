package com.uned.optimizadorga.algoritmo.interfaces;

import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;

/**
 * Interfaz para implementar el patron observer para registrar el progreso del
 * calculo del algoritmo
 * 
 * @author Francisco Javier Garc�a Paredero
 *
 */
public interface AlgorithmSubject {
	/**
	 * Los elementos que quieren recibir actualizaciones sobre el progreso del
	 * calculo se registran ante el algoritmo. En este caso se trata de los
	 * elementos del interfaz grafico, que deben implementar el interfaz
	 * AlgoritmoObserver si desean que se les manden actualizaciones
	 * 
	 * @param observer
	 */
	public void registerObserver(AlgorithmObserver observer);
	
	/**
	 * Cuando se termina el calculo de una era se actualiza el estado de los
	 * observadores
	 * 
	 * Fundamentalmente se trata de la barra de progreso y de los resultados
	 * parciales en el interfaz
	 * 
	 * @param eraProcesada
	 */
	public void notifyEndEraExecution(Era eraProcesada);
	
	/**
	 * Cuando se termina el calculo de una generaci�n se actualiza el estado de
	 * los observadores
	 * 
	 * Fundamentalmente los resultados parciales y la barra de progreso en el
	 * interfaz
	 * 
	 * @param generacionProcesada
	 */
	public void notifyEndGenerationExecution(Generation generacionProcesada);
	
	/**
	 * Cuando finaliza el c�lculo del algoritmo se notifica a los observadores
	 */
	public void notifyEndExecution();

	/**
	 * Si se produce un error enla ejecucion se notifica para que los
	 * observadores puedan volver a una situaci�n controlada
	 */
	public void notifyError(Exception e);
}
