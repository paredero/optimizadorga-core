package com.uned.optimizadorga.algorithm;

import com.uned.optimizadorga.model.Configuration;

/**
 * Implementation of an algorithm that runs Eras synchronously
 * 
 * @author fpb
 *
 */
public class SynchronousAlgorithm extends Algorithm {

	public SynchronousAlgorithm(Configuration configuration) {
		super(configuration);
	}

	@Override
	protected void eraExecution() throws Exception {
		for (int currentEra = 1; currentEra <= configuration.getMaxEras(); currentEra++) {
			Era era = new Era(configuration);
			// The algorithm observs the eras execution
			// so it can infom about the progress
			era.registerObserver(this);
			era.execute();
			this.notifyEndEraExecution(era);
		}
	}

}
