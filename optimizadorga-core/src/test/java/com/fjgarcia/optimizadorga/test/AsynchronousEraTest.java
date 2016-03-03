package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.AsynchronousEra;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

public class AsynchronousEraTest {

	AsynchronousEra e;
	Configuration config; 
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration();
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.algorithm.Generation#execute()}.
	 * @throws Exception 
	 */
	@Test
	public void testRun() throws Exception {
		long startTime = System.nanoTime();
		AsynchronousEra e = new AsynchronousEra(config);
		List<Population> evolution = e.call();
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		for (Population population:evolution) {
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
				crCompare.calculateFitness(config.getFitnessFunction());
				Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
			}
		}
	}
	
	@Test
	public void testRunAsynchronously() {
		long startTime = System.nanoTime();
		AsynchronousEra e = new AsynchronousEra(config);
		try {
			Future<List<Population>> futureResult = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4).submit(e);
			List<Population> evolvedPopulation = futureResult.get();
			System.out.println("TotalTime: " + (System.nanoTime() - startTime));
			// Tiempo incial 16-12:17:24 PT0.503S
			for (Population population:evolvedPopulation) {
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
					crCompare.calculateFitness(config.getFitnessFunction());
					Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
				}
			}
		} catch (Exception ez) {
			ez.printStackTrace();
		}
	}

}
