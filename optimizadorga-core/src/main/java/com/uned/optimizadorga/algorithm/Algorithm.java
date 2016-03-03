package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmSubject;
import com.uned.optimizadorga.algorithm.observerinterfaces.EraObserver;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;
/**
 * Implementation of the evolutionary algorithm
 * It implements several interfaces
 * 	Runnable: So it can run asynchronously from the caller
 * 	AlgorithmSubject: So it can be observed by external classes
 * 	EraObservers: So it can be updated about the progress of each era
 * 
 * @author Francisco Javier García Paredero
 *
 */
public class Algorithm implements Runnable, AlgorithmSubject, EraObserver {

	protected Configuration configuration;
	// Keeps the list of observers
	protected List<AlgorithmObserver> observers;
	
	public Algorithm(Configuration configuracion) {
		this.configuration = configuracion;
		observers = new ArrayList<AlgorithmObserver>();	
	}

	/**
	 * Runs the configurated eras
	 */
	@Override
	public void run() {	
		try {
			for (int currentEra = 1; currentEra <= configuration.getMaxEras(); currentEra++) {
				Era era = new Era(configuration);
				// The algorithm observs the eras execution
				// so it can infom about the progress
				era.registerObserver(this);
				era.execute();
				this.notifyEndEraExecution(era);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.notifyError(e);
		}
		this.notifyEndExecution();
	}


	/*
	 * -------------------------------------------------------------------------
	 * BEHAVIOUR AS AN ALGORITHM SUBJECT
	 * 	As an algorithm subject it provides functionality for:
	 * 		register an observer
	 * 		send a notification when the execution of an era finishes
	 * 		send a notification when the execution of a generation finishes
	 * 		send a notification when the execution of the algorithm finishes
	 * 		send a notification when an error is detected
	 */

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
	public void updateGeneracion(Generation generacionProcesada) {
		// when the algoritm receives an update about the finishing of a generation
		// it just routes the result to the observers
		this.notifyEndGenerationExecution(generacionProcesada);
	}

	@Override
	public void notifyEndEraExecution(List<Population> resultEra) {
		// TODO Auto-generated method stub
		
	}
	
	
}
