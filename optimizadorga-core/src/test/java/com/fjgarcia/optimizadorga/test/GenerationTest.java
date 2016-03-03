/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

/**
 * @author fjgarcia
 * 
 */
public class GenerationTest {
	Generation g;
	Configuration config; 
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		config = TestObjectsBuilder.buildConfiguration();
		Population p = Population.generateInitializedPopulation(config);
		g = new Generation(p, config);
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.algorithm.Generation#execute()}.
	 */

	@Test
	public void testSelect() {
		long startTime = System.nanoTime();
		Population destinationPopulation = g.select();
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		// Will check that all of the chromosomes in the new population exist both in the cache and in the old population
		for (Chromosome c:destinationPopulation.getChromosomes()) {
			assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			boolean foundInOldPopulation = false;
			for (Chromosome oldC:g.getInitialPopulation().getChromosomes()) {
				if (oldC == c) {	// I really want to find the same instance in the old population
					foundInOldPopulation = true;
				}
			}
			assertTrue("New instance created during selection", foundInOldPopulation);
		}
	}
	
	@Test
	public void testCrossover() throws Exception {
		long startTime = System.nanoTime();
		Population crossedPopulation = g.crossover(g.getInitialPopulation());
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		assertEquals("Wrong number of elements in the initial population",g.getInitialPopulation().getSize(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the initial population",g.getInitialPopulation().getChromosomes().size(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the crossed population",crossedPopulation.getSize(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the crossed population",crossedPopulation.getChromosomes().size(), config.getPopulationSize());
		for (Chromosome c:crossedPopulation.getChromosomes()) {
			// Will check that all of the chromosomes in the new population exist in the cache
			assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the new population have a fitness
			Assert.assertNotEquals("Crossed chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the new population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Crossed chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
		for (Chromosome c:g.getInitialPopulation().getChromosomes()) {
			// Will check that all of the chromosomes in the initial population exist in the cache
			assertTrue("Original Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the initial population have a fitness
			Assert.assertNotEquals("Original chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the original population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Original chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
	}
	
	@Test
	public void testMutation() throws Exception {
		long startTime = System.nanoTime();
		Population originalPopulation = Population.copyPopulation(g.getInitialPopulation());
		Population mutatedPopulation = g.mutation(g.getInitialPopulation());
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), mutatedPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",mutatedPopulation.getChromosomes().size(), config.getPopulationSize());

		// Will check that initial population was not modified
		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		for (int i = 0; i<originalPopulation.getSize(); i++) {
			Chromosome originalChromosome = originalPopulation.getChromosomes().get(i);
			Chromosome initialChromosome = g.getInitialPopulation().getChromosomes().get(i);
			assertTrue("Chromosome reference modified in the original Population", originalChromosome==initialChromosome);
			assertEquals("Chromosome modified in the initial population",originalChromosome, initialChromosome);
		}
		
		for (Chromosome c:mutatedPopulation.getChromosomes()) {
			// Will check that all of the chromosomes in the new population exist in the cache
			assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the new population have a fitness
			Assert.assertNotEquals("Mutated chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the new population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
		for (Chromosome c:g.getInitialPopulation().getChromosomes()) {
			// Will check that all of the chromosomes in the initial population exist in the cache
			assertTrue("Original Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the initial population have a fitness
			Assert.assertNotEquals("Original chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the original population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Original chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
	}

	@Test
	public void testElitism() throws Exception {

		long startTime = System.nanoTime();
		Population originalPopulation = Population.copyPopulation(g.getInitialPopulation());
		Population elitistPopulation = g.elitism(originalPopulation);
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), elitistPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",elitistPopulation.getChromosomes().size(), config.getPopulationSize());

		// Will check that initial population was not modified
		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		for (int i = 0; i<originalPopulation.getSize(); i++) {
			Chromosome originalChromosome = originalPopulation.getChromosomes().get(i);
			Chromosome initialChromosome = g.getInitialPopulation().getChromosomes().get(i);
			assertTrue("Chromosome reference modified in the original Population", originalChromosome==initialChromosome);
			assertEquals("Chromosome modified in the initial population",originalChromosome, initialChromosome);
		}
		
		for (Chromosome c:elitistPopulation.getChromosomes()) {
			// Will check that all of the chromosomes in the new population exist in the cache
			assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the new population have a fitness
			Assert.assertNotEquals("Mutated chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the new population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
		for (Chromosome c:g.getInitialPopulation().getChromosomes()) {
			// Will check that all of the chromosomes in the initial population exist in the cache
			assertTrue("Original Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the initial population have a fitness
			Assert.assertNotEquals("Original chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the original population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Original chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}	
	}
	
	@Test
	public void testExecution() throws Exception {
		long startTime = System.nanoTime();
		Population originalPopulation = Population.copyPopulation(g.getInitialPopulation());
		Population evolvedPopulation = g.execute();
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), evolvedPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",evolvedPopulation.getChromosomes().size(), config.getPopulationSize());

		// Will check that initial population was not modified
		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		for (int i = 0; i<originalPopulation.getSize(); i++) {
			Chromosome originalChromosome = originalPopulation.getChromosomes().get(i);
			Chromosome initialChromosome = g.getInitialPopulation().getChromosomes().get(i);
			assertTrue("Chromosome reference modified in the original Population", originalChromosome==initialChromosome);
			assertEquals("Chromosome modified in the initial population",originalChromosome, initialChromosome);
		}
		
		for (Chromosome c:evolvedPopulation.getChromosomes()) {
			// Will check that all of the chromosomes in the new population exist in the cache
			assertTrue("New Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the new population have a fitness
			Assert.assertNotEquals("Mutated chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the new population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Mutated chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
		for (Chromosome c:g.getInitialPopulation().getChromosomes()) {
			// Will check that all of the chromosomes in the initial population exist in the cache
			assertTrue("Original Chromosome not existing in cache",  config.getChromosomeCache().containsKey(c));
			// Will check that all of the chromosomes in the initial population have a fitness
			Assert.assertNotEquals("Original chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			// Will check that all of the chromosomes in the original population have the right fitness
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Original chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}	
	}
}
