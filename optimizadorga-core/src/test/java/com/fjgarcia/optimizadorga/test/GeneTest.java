/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

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
		long startTime = System.nanoTime();
		int iterations = 10000;
		long[] allElapsedTimes = new long[2*iterations]; 
		for (int i = 0; i < iterations; i++) {
			long iterationStartTime = System.nanoTime();
			param.generateRandomValue();
			assertTrue("The gene value should bethan minimum" + param,
					param.getValue() >= 0);
			assertTrue("The gene value should be lower than maximum" + param,
					param.getValue() <= 100);
			// Now it tests the formatting
			String value = Double.toString(param.getValue());
			String[] valueParts = value.split("\\.");
			if (valueParts.length > 1) {
				assertTrue("Too many decimals ("+param.getGeneType()
						.getPrecission()+"): "+param.getValue(),
						valueParts[1].length() <= param.getGeneType()
								.getPrecission());
			}
			long iterationEndTime = System.nanoTime();
			allElapsedTimes[i] = iterationEndTime - iterationStartTime;
			System.out.println(iterationEndTime - iterationStartTime);
		}

		for (int i = 0; i < iterations; i++) {
			long iterationStartTime = System.nanoTime();
			param2.generateRandomValue();
			assertTrue("Gene value should be bigger than minimum" + param2,
					param2.getValue() >= param2.getGeneType().getMin());
			assertTrue("Gene value should be lower than maximum" + param2,
					param2.getValue() <= param2.getGeneType().getMax());
			// Now it tests the formatting
			String value = Double.toString(param2.getValue());
			String[] valueParts = value.split("\\.");
			if (valueParts.length > 1) {
				assertTrue("Too many decimals: ("+param2.getGeneType()
						.getPrecission()+"): "+param2.getValue(),
						valueParts[1].length() <= param2.getGeneType()
						.getPrecission());
			}
			long iterationEndTime = System.nanoTime();
			allElapsedTimes[i+100] = iterationEndTime - iterationStartTime;
			System.out.println(iterationEndTime - iterationStartTime);
		}
		long avgTime = 0;
		for (int i = 0; i<2*iterations; i++) {
			avgTime+=allElapsedTimes[i];
		}
		System.out.println("AvgTime: "+avgTime/(2*iterations));
		System.out.println("TotalTime: "+(System.nanoTime()-startTime));
	}

}
