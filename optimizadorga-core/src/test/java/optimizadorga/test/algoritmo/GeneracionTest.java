/**
 * 
 */
package optimizadorga.test.algoritmo;

import static org.junit.Assert.assertEquals;
import optimizadorga.test.util.TestObjectsBuilder;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

/**
 * @author fjgarcia
 * 
 */
public class GeneracionTest {
	Generation g;
	private Configuration configuration;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		configuration = TestObjectsBuilder.buildConfiguration();

		Population p = Population.generateInitializedPopulation(configuration);
		g = new Generation(p, configuration);
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.algorithm.Generation#execute()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExecute() throws Exception {
		g.execute();
		assertEquals("Unexpected Evolved Population size",
				configuration.getPopulationSize(), g.getEvolvedPopulation()
						.getSize());
		assertEquals("Unexpected Initial Population size",
				configuration.getPopulationSize(), g.getInitialPopulation()
						.getSize());
	}

}
