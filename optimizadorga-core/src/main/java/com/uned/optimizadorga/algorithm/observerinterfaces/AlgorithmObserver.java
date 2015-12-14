/**
 * 
 */
package com.uned.optimizadorga.algorithm.observerinterfaces;

import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;

/**
 * Observer interface to keep updated of the algorithm progress.
 * This interface must be implemented by those elements that want to receive
 * updates when the algorithm progress.
 * 
 * ie the GUI must implement this interface if it wants to receive updates and
 * show parcial results
 * 
 * @author Francisco Javier Garcia Paredero
 */
public interface AlgorithmObserver {
	
	/**
	 * Method used to send an update about the end of the execution of an era to
	 * an observer
	 * 
	 * @param processedEra
	 */
	public void updateEndEraExecution(Era processedEra);

	/**
	 * Method used to send an update to an observer about the end of the
	 * execution of a generation
	 * 
	 * @param processedGeneration
	 */
	public void updateEndGenerationExecution(Generation processedGeneration);

	/**
	 * Method used to send an update to an observer when the algorithm execution
	 * finishes
	 */
	public void updateEnd();

	/**
	 * Method used to send an update to an observer when an error occurs
	 */
	public void updateError(Exception e);
}
