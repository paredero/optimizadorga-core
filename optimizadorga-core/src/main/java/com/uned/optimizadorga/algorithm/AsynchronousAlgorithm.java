package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.uned.optimizadorga.model.Configuration;

public class AsynchronousAlgorithm extends Algorithm {

	public AsynchronousAlgorithm(Configuration configuration) {
		super(configuration);
	}

	@Override
	protected void eraExecution() throws Exception {
		List<Era> allEras = new ArrayList<Era>(configuration.getMaxEras());
		for (int currentEra = 1; currentEra <= configuration.getMaxEras(); currentEra++) {
			Era era = new Era(configuration);
			// The algorithm observs the eras execution
			// so it can infom about the progress
			era.registerObserver(this);
			allEras.add(era);
		}
		ExecutorService pool = Executors.newCachedThreadPool();
		List<Future<Era>> result = pool.invokeAll(allEras);
		for (Future<Era> f : result) {
			this.notifyEndEraExecution(f.get());
		}
	}
}
