/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fjgarcia.optimizadorga.elementos.Gene;
import com.fjgarcia.optimizadorga.elementos.GeneType;

/**
 * @author fpb
 *
 */
public class GeneTest {
	private Gene param;
	private Gene param2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		GeneType tipoGen1 = new GeneType("p1", 0, 100, 5);
		param = new Gene(tipoGen1);

		GeneType tipoGen2 = new GeneType("p2", 2000, 3000, 10);
		param2 = new Gene(tipoGen2);
	}

	/**
	 * Test method for
	 * It tests that the generateRandom
	 * {@link com.Gene.optimizadorga.elementos.Gen#generateRandomValue()}.
	 */
	@Test
	public void testGenerateRandomValue() {
		Date start = new Date();

		for (int i = 0; i < 100; i++) {
			param.generateRandomValue();
			System.out.println(param);
			assertTrue("El valor del gen es mayor que el minimo" + param,
					param.getValue() >= 0);
			assertTrue("El valor del gen es menor que el maximo" + param,
					param.getValue() <= 100);
			// Now it tests the formatting
			String value = Double.toString(param.getValue());
			String[] valueParts = value.split("\\.");
			if (valueParts.length > 1) {
				assertTrue("Demasiados decimales ("+param.getGeneType()
						.getPrecission()+"): "+param.getValue(),
						valueParts[1].length() <= param.getGeneType()
								.getPrecission());
			}
			
		}

		for (int i = 0; i < 100; i++) {
			param2.generateRandomValue();
			System.out.println(param2);
			assertTrue("El valor del gen es mayor que el minimo" + param2,
					param2.getValue() >= param2.getGeneType().getMinimum());
			assertTrue("El valor del gen es menor que el maximo" + param2,
					param2.getValue() <= param2.getGeneType().getMaximum());
			// Now it tests the formatting
			String value = Double.toString(param2.getValue());
			String[] valueParts = value.split("\\.");
			if (valueParts.length > 1) {
				assertTrue("Demasiados decimales: ("+param2.getGeneType()
						.getPrecission()+"): "+param2.getValue(),
						valueParts[1].length() <= param2.getGeneType()
						.getPrecission());
			}
		}
		System.out.println(new Date().getTime() - start.getTime()+" ms");
	}

}
