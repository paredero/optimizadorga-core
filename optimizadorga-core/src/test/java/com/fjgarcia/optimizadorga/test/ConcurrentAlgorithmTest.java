package com.fjgarcia.optimizadorga.test;

import java.util.concurrent.Executors;

import org.junit.Before;

import com.fjgarcia.optimizadorga.test.util.TestObjectsBuilder;
import com.uned.optimizadorga.algorithm.AsynchronousAlgorithm;

public class ConcurrentAlgorithmTest extends AlgorithmTest {

	@Override
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration(Boolean.TRUE);	
		a = new AsynchronousAlgorithm(config);
		a.registerObserver(this);
		pool = Executors.newCachedThreadPool();
	}


}
