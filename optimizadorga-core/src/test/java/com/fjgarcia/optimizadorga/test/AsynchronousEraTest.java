package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fjgarcia.optimizadorga.test.util.TestObjectsBuilder;
import com.uned.optimizadorga.algorithm.AsynchronousEra;
import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

public class AsynchronousEraTest {

	AsynchronousEra e;
	Configuration config;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration(Boolean.TRUE);
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.algorithm.Generation#execute()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRun() throws Exception {
		AsynchronousEra e = new AsynchronousEra(config);
		Era era = e.call();
		for (Population population : era.getEvolvedPopulations()) {
			assertEquals("Wrong number of elements in the  population",
					config.getPopulationSize(), population.getSize());
			assertEquals("Wrong number of elements in the  population",
					config.getPopulationSize(), population.getChromosomes()
							.size());
			for (Chromosome c : population.getChromosomes()) {
				Assert.assertNotEquals("Mutated chromosome has no fitness",
						c.getFitness(), 0.00, 0.00);
				Chromosome crCompare = new Chromosome(c);
				crCompare.calculateFitness(config.getFitnessFunction());
				Assert.assertEquals("Mutated chromosome has wrong fitness",
						c.getFitness(), crCompare.getFitness(), 0.001);
			}
		}
	}

	@Test
	public void testRunAsynchronously() {
		AsynchronousEra e = new AsynchronousEra(config);
		try {
			Future<Era> futureResult = Executors.newFixedThreadPool(
					Runtime.getRuntime().availableProcessors() * 4).submit(e);
			List<Population> evolvedPopulation = futureResult.get()
					.getEvolvedPopulations();
			for (Population population : evolvedPopulation) {
				assertEquals("Wrong number of elements in the  population",
						config.getPopulationSize(), population.getSize());
				assertEquals("Wrong number of elements in the  population",
						config.getPopulationSize(), population.getChromosomes()
								.size());
				for (Chromosome c : population.getChromosomes()) {
					Assert.assertNotEquals("Mutated chromosome has no fitness",
							c.getFitness(), 0.00, 0.00);
					Chromosome crCompare = new Chromosome(c);
					crCompare.calculateFitness(config.getFitnessFunction());
					Assert.assertEquals("Mutated chromosome has wrong fitness",
							c.getFitness(), crCompare.getFitness(), 0.001);
				}
			}
		} catch (Exception ez) {
			ez.printStackTrace();
		}
	}

}
