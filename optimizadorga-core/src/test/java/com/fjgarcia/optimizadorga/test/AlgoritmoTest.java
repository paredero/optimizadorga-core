package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.AsynchronousAlgorithm;
import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

public class AlgoritmoTest implements AlgorithmObserver {

	private Configuration config;
	private AsynchronousAlgorithm a;
	private int notificacionesFin;
	private int notificacionesGeneracion;
	private int notificacionesEra;
	private long startTime = System.nanoTime();
	
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration();	
		a = new AsynchronousAlgorithm(config);
		a.registerObserver(this);
	}


	@Test
	public void testRun() {
		a.run();		
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
		System.out.println("End Execution in "+(System.nanoTime()-startTime));
	}


	@Override
	public void updateError(Exception e) {
		
	}


	@Override
	public void updateEndEraExecution(List<Population> resultEra) {
		notificacionesEra++;
		System.out.println("End Era in "+(System.nanoTime()-startTime));
		for (Population population:resultEra) {
			System.out.println(population);
			// Tiempo incial 16-12:17:24 PT0.503S
			assertEquals("Wrong number of elements in the  population",config.getPopulationSize(), population.getSize());
			assertEquals("Wrong number of elements in the  population",config.getPopulationSize(), population.getChromosomes().size());
			for (Chromosome c:population.getChromosomes()) {
				// Will check that all of the chromosomes in the new population exist in the cache
				assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
				// Will check that all of the chromosomes in the new population have a fitness
				Assert.assertNotEquals("Mutated chromosome has no fitness",c.getFitness(), 0.00, 0.00);
				// Will check that all of the chromosomes in the new population have the right fitness
				Chromosome crCompare = new Chromosome(c);
				try {
					crCompare.calculateFitness(config.getFitnessFunction());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
			}
		}
	}

}
