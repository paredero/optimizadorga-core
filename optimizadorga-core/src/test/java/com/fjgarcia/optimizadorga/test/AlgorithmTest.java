package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import optimizadorga.test.util.TestObjectsBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Algorithm;
import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.algorithm.SynchronousAlgorithm;
import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

public class AlgorithmTest implements AlgorithmObserver {
	protected Configuration config;
	protected Algorithm a;
	protected int notificacionesFin;
	protected int notificacionesGeneracion;
	protected int notificacionesEra;
	
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration();	
		a = new SynchronousAlgorithm(config);
		a.registerObserver(this);
	}


	@Test
	public void testRun() throws Exception {
		a.call();		
	}

	@Test(expected=Exception.class)
	public void testRunFail() throws Exception {
		config.setFitnessFunction(null);
		a = new SynchronousAlgorithm(config);
		a.call();
		fail();
	}
	
	@Test
	public void testRegisterObserver() {
//		fail("Not yet implemented");
	}

	@Test
	public void testNotifyEra() {
//		fail("Not yet implemented");
	}

	@Test
	public void testNotifyGeneracion() {
//		fail("Not yet implemented");
	}

	@Test
	public void testNotifyFin() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetConfiguracion() {
//		fail("Not yet implemented");
	}

	@Test
	public void testUpdateGeneracion() {
//		fail("Not yet implemented");
	}


	@Override
	public void updateEndEraExecution(Era resultadoParcial) {
		notificacionesEra++;
	}


	@Override
	public void updateEndGenerationExecution(Generation resultadoParcial) {
		notificacionesGeneracion++;
//		System.out.println("End generation in "+(System.nanoTime()-startTime));
	}


	@Override
	public void updateEnd() {
		notificacionesFin++;
	}


	@Override
	public void updateError(Exception e) {
		
	}


	@Override
	public void updateEndEraExecution(List<Population> resultEra) {
		notificacionesEra++;
		for (Population population:resultEra) {
			assertEquals("Wrong number of elements in the  population",config.getPopulationSize(), population.getSize());
			assertEquals("Wrong number of elements in the  population",config.getPopulationSize(), population.getChromosomes().size());
			for (Chromosome c:population.getChromosomes()) {
				Assert.assertNotEquals("Mutated chromosome has no fitness",c.getFitness(), 0.00, 0.00);
				Chromosome crCompare = new Chromosome(c);
				try {
					crCompare.calculateFitness(config.getFitnessFunction());
				} catch (Exception e) {
					e.printStackTrace();
				}
				Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
			}
		}
	}

}