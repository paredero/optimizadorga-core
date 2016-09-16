/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
		Population destinationPopulation = g.select();
		for (Chromosome c:destinationPopulation.getChromosomes()) {
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
		checkPopulationFitness(g.getInitialPopulation());
		Population crossedPopulation = g.crossover(g.getInitialPopulation());
		assertEquals("Wrong number of elements in the initial population",g.getInitialPopulation().getSize(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the initial population",g.getInitialPopulation().getChromosomes().size(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the crossed population",crossedPopulation.getSize(), config.getPopulationSize());
		assertEquals("Wrong number of elements in the crossed population",crossedPopulation.getChromosomes().size(), config.getPopulationSize());
		checkPopulationFitness(crossedPopulation);
		checkPopulationFitness(g.getInitialPopulation());
	}

	/**
	 * @param population
	 * @throws Exception
	 */
	private void checkPopulationFitness(Population population)
			throws Exception {
		for (Chromosome c:population.getChromosomes()) {
			Assert.assertNotEquals("Chromosome has no fitness",c.getFitness(), 0.00, 0.00);
			Chromosome crCompare = new Chromosome(c);
			crCompare.calculateFitness(config.getFitnessFunction());
			Assert.assertEquals("Chromosome has wrong fitness", c.getFitness(), crCompare.getFitness(), 0.001);
		}
	}
	
	@Test
	public void testMutation() throws Exception {
		Population originalPopulation = copyPopulation(g.getInitialPopulation());
		this.checkPopulationFitness(g.getInitialPopulation());
		Population mutatedPopulation = g.mutation(g.getInitialPopulation());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), mutatedPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",mutatedPopulation.getChromosomes().size(), config.getPopulationSize());
		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		
		this.checkPopulationFitness(mutatedPopulation);
		this.checkPopulationFitness(g.getInitialPopulation());
	}

	@Test
	public void testElitism() throws Exception {
		Population originalPopulation = copyPopulation(g.getInitialPopulation());
		this.checkPopulationFitness(originalPopulation);
		Population elitistPopulation = g.elitism(originalPopulation);
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), elitistPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",elitistPopulation.getChromosomes().size(), config.getPopulationSize());

		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		
		this.checkPopulationFitness(elitistPopulation);
		this.checkPopulationFitness(g.getInitialPopulation());	
	}
	
	@Test
	public void testExecution() throws Exception {
		Population originalPopulation = copyPopulation(g.getInitialPopulation());
		this.checkPopulationFitness(originalPopulation);
		Population evolvedPopulation = g.execute();
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getSize());
		assertEquals("Wrong number of elements in the initial population",config.getPopulationSize(), g.getInitialPopulation().getChromosomes().size());
		assertEquals("Wrong number of elements in the mutated population",config.getPopulationSize(), evolvedPopulation.getSize());
		assertEquals("Wrong number of elements in the mutated population",evolvedPopulation.getChromosomes().size(), config.getPopulationSize());
		assertEquals("Modified size in initial population", originalPopulation.getSize(), g.getInitialPopulation().getSize());
		assertEquals("Modified size in initial population", originalPopulation.getChromosomes().size(), g.getInitialPopulation().getChromosomes().size());
		this.checkPopulationFitness(evolvedPopulation);
		this.checkPopulationFitness(g.getInitialPopulation());
	}
	
	/**
	 * Copies a population, chromosomes included
	 * @param originalPopulation
	 * @return
	 */
	private Population copyPopulation(Population originalPopulation) {
		Population copy = Population.copyEmptyPopulation(originalPopulation);
		copy.setFitnessFunction(originalPopulation.getFitnessFunction());
		copy.setSize(originalPopulation.getSize());
		List<Chromosome> chromosomeList = new ArrayList<Chromosome>();
		for (Chromosome c:originalPopulation.getChromosomes()) {
			Chromosome newChromosome = new Chromosome(c);
			chromosomeList.add(newChromosome);
		}
		copy.setChromosomes(chromosomeList);
		return copy;
	}
}
