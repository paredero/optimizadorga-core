package com.fjgarcia.optimizadorga.test;

import optimizadorga.test.util.TestObjectsBuilder;

import org.junit.Before;

import com.uned.optimizadorga.algorithm.ConcurrentAlgorithm;

public class ConcurrentAlgorithmTest extends AlgorithmTest {

	@Override
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration();	
		a = new ConcurrentAlgorithm(config);
		a.registerObserver(this);
	}


}
