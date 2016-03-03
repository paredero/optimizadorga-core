/**
 * 
 */
package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

/**
 * @author javier.garciapareder
 *
 */
public class AsynchronousAlgorithm extends Algorithm {
	public AsynchronousAlgorithm(Configuration configuracion) {
		super(configuracion);
	}

	/**
	 * Runs the configurated eras
	 */
	@Override
	public void run() {	
		try {
			ExecutorService executor = Executors.newCachedThreadPool();
			List<AsynchronousEra> allEras = new ArrayList<>();
			for (int currentEra = 1; currentEra <= configuration.getMaxEras(); currentEra++) {
				AsynchronousEra era = new AsynchronousEra(configuration);
				// The algorithm observs the eras execution
				// so it can infom about the progress
				era.registerObserver(this);
//				this.notifyEndEraExecution(era);
				allEras.add(era);
			}
			List<Future<List<Population>>> futureResults = executor.invokeAll(allEras);
			for (Future<List<Population>> f:futureResults) {
				List<Population> resultEra = f.get();
				this.notifyEndEraExecution(resultEra);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.notifyError(e);
		}
		this.notifyEndExecution();
	}

	@Override
	public  void notifyEndEraExecution(List<Population> resultEra) {
		for (AlgorithmObserver o:this.observers) {
			o.updateEndEraExecution(resultEra);
		}
	}

}
