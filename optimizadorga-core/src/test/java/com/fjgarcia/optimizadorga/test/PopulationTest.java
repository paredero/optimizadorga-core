/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

/**
 * @author fpb
 *
 */
public class PopulationTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.uned.optimizadorga.model.Population#generarPoblacionInicializada(int, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testGenerarPoblacionInicializada() throws Exception {
		Configuration c = TestObjectsBuilder.buildConfiguration();
		long startTime = System.nanoTime();
		for (int j=0; j<c.getMaxEras();j++) {
			Population p = Population.generateInitializedPopulation(c);
			for (Chromosome cr:p.getChromosomes()) {
				Assert.assertNotEquals(cr.getFitness(), 0.00, 0.00);
				Chromosome crCompare = new Chromosome(cr);
				crCompare.calculateFitness(c.getFitnessFunction());
				Assert.assertEquals(cr.getFitness(), crCompare.getFitness(), 0.001);
			}
		}
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
	}
}
