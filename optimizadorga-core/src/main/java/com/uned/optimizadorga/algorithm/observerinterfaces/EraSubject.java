package com.uned.optimizadorga.algorithm.observerinterfaces;

import com.uned.optimizadorga.algorithm.Generation;

/**
 * Interface used to register the progress in the execution of an era
 * It provides with the methods for the era to update about its progress
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public interface EraSubject {
	/**
	 * Those elements that want to receive updates about the progress of the era
	 * execution must register themselves as observers
	 * 
	 * @param observer
	 */
	public void registerObserver(EraObserver observer);
	
	/**
	 * It notifies all observers when a generation execution has finishedS
	 * 
	 * @param processedGeneration
	 */
	public void notifyGeneracion(Generation processedGeneration);
}
