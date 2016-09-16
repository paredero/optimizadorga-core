package com.uned.optimizadorga.algorithm;

import java.util.concurrent.Callable;

import com.uned.optimizadorga.model.Configuration;

public class AsynchronousEra extends Era implements Callable<Era> {

	public AsynchronousEra(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Era call() throws Exception {
		this.execute();
		return this;
	}
	
	
}
