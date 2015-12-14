package com.uned.optimizadorga.algorithm.observerinterfaces;

import com.uned.optimizadorga.algorithm.Generation;

/**
 * Interface used to implement the observer pattern in the relation between an algorithm and an era
 * 
 * In an era, the state changes when a new generation has occurred
 * 
 * @author Francisco Javier Garcia Paredero
 * 
 */
public interface EraObserver {

	/**
	 * When a new generation has finished its calculations the observers are updated
	 * 
	 * @param S
	 */
	public void updateGeneracion(Generation processedGeneration);
}
