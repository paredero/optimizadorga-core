/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.GeneType;

/**
 * @author fpb
 *
 */
public class ChromosomeTest {
	Map<String, GeneType> parameterGenes;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parameterGenes = new HashMap<String, GeneType>();
		parameterGenes.put("p1", new GeneType("p1", 0, 100, 3));
		parameterGenes.put("p2", new GeneType("p2", 100, 200, 3));
		parameterGenes.put("p3", new GeneType("p3", 200, 300, 3));
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.Chromosome.Cromosoma#generarAleatorio(java.util.List)}
	 * .
	 */
	@Test
	public void testGenerateRandom() {
		int iterations = 100;
		Chromosome c = null;
		for (int i = 0; i < iterations; i++) {
			c = Chromosome.generateRandomChromosome(parameterGenes);
			assertNotNull("Chromosome not created", c);
			assertNotNull("Gene list not created", c.getGenes());
			assertTrue("Gene list not filled with genes " + c.getGenes().size(), c.getGenes().size() == 3);
			assertTrue("p1 gene value should be smaller than maximum" + c.getGenes().get(0),
					c.getGenes().get(0).getValue() <= c.getGenes().get(0).getGeneType().getMax());
			assertTrue("p1 gene value should be bigger than minimum" + c.getGenes().get(0),
					c.getGenes().get(0).getValue() >= c.getGenes().get(0).getGeneType().getMin());

			assertTrue("p2 gene value should be smaller than maximum" + c.getGenes().get(1),
					c.getGenes().get(1).getValue() <= c.getGenes().get(1).getGeneType().getMax());
			assertTrue("p2 gene value should be bigger than minimum" + c.getGenes().get(1),
					c.getGenes().get(1).getValue() >= c.getGenes().get(1).getGeneType().getMin());

			assertTrue("p3 gene value should be smaller than maximum" + c.getGenes().get(2),
					c.getGenes().get(2).getValue() <= c.getGenes().get(2).getGeneType().getMax());
			assertTrue("p3 gene value should be bigger than minimum" + c.getGenes().get(2),
					c.getGenes().get(2).getValue() >= c.getGenes().get(2).getGeneType().getMin());
		}
	}

}
