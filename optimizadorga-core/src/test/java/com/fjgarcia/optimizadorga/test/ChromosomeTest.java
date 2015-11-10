/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fjgarcia.optimizadorga.elementos.Chromosome;
import com.fjgarcia.optimizadorga.elementos.GeneType;

/**
 * @author fpb
 *
 */
public class ChromosomeTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.uned.optimizadorga.Chromosome.Cromosoma#generarAleatorio(java.util.List)}.
	 */
	@Test
	public void testGenerarAleatorio() {
		Collection<GeneType> genesParametro = new ArrayList<GeneType>();
		genesParametro.add(new GeneType("p1",0, 100, 3));
		genesParametro.add(new GeneType("p2",100, 200, 3));
		genesParametro.add(new GeneType("p3",200, 300, 3));
		Chromosome c = Chromosome
				.generateRandomChromosome(genesParametro);
		
		assertNotNull("El cromosoma no se ha creado", c);
		assertNotNull("La lista de genes no se ha creado", c.getGenes());	
		assertTrue("Los genes no se han incluido dentro de la lista "
				+ c.getGenes().size(), c.getGenes().size() == 3);
		assertTrue("Valor Erroneo en gen p1" + c.getGenes().get(0), c
				.getGenes().get(0).getValue() <= c
						.getGenes().get(0).getGeneType().getMaximum());
		assertTrue("Valor Erroneo en gen p1" + c.getGenes().get(0), c
				.getGenes().get(0).getValue() >= c
						.getGenes().get(0).getGeneType().getMinimum());

		assertTrue("Valor Erroneo en gen p2" + c.getGenes().get(1), c
				.getGenes().get(1).getValue() <= c
						.getGenes().get(1).getGeneType().getMaximum());
		assertTrue("Valor Erroneo en gen p2" + c.getGenes().get(1), c
				.getGenes().get(1).getValue() >= c
						.getGenes().get(1).getGeneType().getMinimum());

		assertTrue("Valor Erroneo en gen p3" + c.getGenes().get(2), c
				.getGenes().get(2).getValue() <= c
						.getGenes().get(2).getGeneType().getMaximum());
		assertTrue("Valor Erroneo en gen p3" + c.getGenes().get(2), c
				.getGenes().get(2).getValue() >= c
						.getGenes().get(2).getGeneType().getMinimum());		
	}

}
