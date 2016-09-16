package com.uned.optimizadorga.algorithm.observerinterfaces;

import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;

/**
 * Interface to implement the observer pattern so the progress of the algoritm
 * can be seen by an observer
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public interface AlgorithmSubject {
	/**
	 * Those elements that want to act as observers to the algorithm must register
	 * themselves.
	 * 
	 * @param observer
	 */
	public void registerObserver(AlgorithmObserver observer);
	
	/**
	 * When the execution of an era finishes the observers are notified
	 * 
	 * ie. a GUI that wants to show partial results will receive that data
	 * through this method
	 * 
	 * @param processedEra
	 */
	public void notifyEndEraExecution(Era processedEra);
	
	/**
	 * When the execution of a generation era finishes, the observers are notified
	 * 
	 * ie. a GUI that wants to show partial results will receive that data
	 * through this method
	 * 
	 * @param processedGeneration
	 */
	public void notifyEndGenerationExecution(Generation processedGeneration);
	
	/**
	 * When the execution of the algorithm finishes, the observers are notified
	 */
	public void notifyEndExecution();

	/**
	 * If an error happens during execution, the observers are notified
	 */
	public void notifyError(Exception e);

}
