package com.fjgarcia.optimizadorga.test;

import org.junit.Before;

import com.fjgarcia.optimizadorga.test.util.TestObjectsBuilder;
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
