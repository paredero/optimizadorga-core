package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmSubject;
import com.uned.optimizadorga.algorithm.observerinterfaces.EraObserver;
import com.uned.optimizadorga.model.Configuration;
/**
 * Implementation of the evolutionary algorithm
 * It implements several interfaces
 * 	Runnable: So it can run asynchronously from the caller
 * 	AlgorithmSubject: So it can be observed by external classes. It provides functionality for:
 * 		register an observer
 * 		send a notification when the execution of an era finishes
 * 		send a notification when the execution of a generation finishes
 * 		send a notification when the execution of the algorithm finishes
 * 		send a notification when an error is detected
 * 	EraObservers: So it can be updated about the progress of each era
 * 
 * @author Francisco Javier García Paredero
 *
 */
public abstract class Algorithm implements Callable, AlgorithmSubject, EraObserver {

	protected Configuration configuration;
	// Keeps the list of observers
	protected List<AlgorithmObserver> observers;
	
	public Algorithm(Configuration configuration) {
		this.configuration = configuration;
		observers = new ArrayList<AlgorithmObserver>();	
	}

	/**
	 * Runs the configurated number of eras
	 */
	@Override
	public Object call() throws Exception {	
		try {
			eraExecution();
		} catch (Exception e) {
			this.notifyError(e);
			throw e;
		}
		this.notifyEndExecution();
		return null;
	}

	/**
	 * @throws Exception
	 */
	protected abstract void eraExecution() throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.interfaces.AlgoritmoSubject#registerObserver
	 * (com.uned.optimizadorga.algoritmo.interfaces.AlgoritmoObserver)
	 */
	@Override
	public void registerObserver(AlgorithmObserver observer) {
		this.observers.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.interfaces.AlgoritmoSubject#notifyEra
	 * (com.uned.optimizadorga.algoritmo.Era)
	 */
	@Override
	public void notifyEndEraExecution(Era processedEra) {
		for (AlgorithmObserver o:this.observers) {
			o.updateEndEraExecution(processedEra);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.interfaces.AlgoritmoSubject#notifyGeneracion
	 * (com.uned.optimizadorga.algoritmo.Generacion)
	 */
	@Override
	public void notifyEndGenerationExecution(Generation processedGeneration) {
		for (AlgorithmObserver o:this.observers) {
			o.updateEndGenerationExecution(processedGeneration);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.interfaces.AlgoritmoSubject#notifyFin()
	 */
	@Override
	public void notifyEndExecution() {
		// When it finishes it sends all the eras, which already contains all the
		// processed generations
		for (AlgorithmObserver o:this.observers) {
			o.updateEnd();
		}
	}
	
	@Override
	public void notifyError(Exception e) {
		// When an error is detected, it notifies it to the observers
		for (AlgorithmObserver o:this.observers) {
			o.updateError(e);
		}
	}

	/**
	 * @return the configuracion
	 */
	public Configuration getConfiguracion() {
		return this.configuration;
	}
	
	/*
	 *	As an era observer it receives updates when a generation finishes 
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.interfaces.EraObserver#updateGeneracion
	 * (com.uned.optimizadorga.algoritmo.Generacion)
	 */
	@Override
	public void updateGeneracion(Generation processedGeneration) {
		// when the algoritm receives an update about the finishing of a generation
		// it just routes the result to the observers
		this.notifyEndGenerationExecution(processedGeneration);
	}

	/**
	 * Factory method for algorithms
	 */
	public static Algorithm create(Configuration configuration) {
		if (configuration.isAsynchronous()) {
			return new AsynchronousAlgorithm(configuration);
		} else {
			return new SynchronousAlgorithm(configuration);
		}
	}
}
