/**
 * 
 */
package optimizadorga.test.algoritmo;

import java.time.Duration;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

/**
 * @author fjgarcia
 * 
 */
public class GeneracionTest {
	Generation g;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Configuration c = TestObjectsBuilder.buildConfiguration();
		
		Population p = Population.generateInitializedPopulation(c);
		g = new Generation(p, c);
	}

	/**
	 * Test method for
	 * {@link com.uned.optimizadorga.algorithm.Generation#execute()}.
	 */
	@Test
	public void testEjecutar() {
		Instant start = Instant.now();
		try {
			g.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Duration.between(start, Instant.now()));
		// Tiempo incial 16-12:17:24 PT0.503S
	}

}
