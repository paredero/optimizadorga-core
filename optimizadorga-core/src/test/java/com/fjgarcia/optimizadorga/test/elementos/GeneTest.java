/**
 * 
 */
package com.fjgarcia.optimizadorga.test.elementos;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.model.Gene;
import com.uned.optimizadorga.model.GeneType;

/**
 * @author fpb
 *
 */
public class GeneTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.uned.optimizadorga.model.Gene#generateRandomValue()}.
	 */
	@Test
	public void testGenerateRandomValue() {
		GeneType geneType = new GeneType("p1",0, 100, 5);
		Gene parameter = new Gene(geneType);
		parameter.generateRandomValue();
		assertTrue("El valor del gen es mayor que el minimo"+parameter, parameter.getValue() >= 0);
		assertTrue("El valor del gen es menor que el maximo"+parameter, parameter.getValue() <= 100);		
	}

}
