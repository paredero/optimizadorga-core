package com.uned.optimizadorga.algorithm;

import java.util.List;
import java.util.concurrent.Callable;

import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

public class AsynchronousEra extends Era implements Callable<List<Population>> {

	public AsynchronousEra(Configuration configuration) {
		super(configuration);
	}

	@Override
	public List<Population> call() throws Exception {
		this.execute();
		return this.evolvedPopulations;
	}
	
	
}
