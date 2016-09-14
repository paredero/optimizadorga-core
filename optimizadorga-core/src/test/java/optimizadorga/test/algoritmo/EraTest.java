/**
 * 
 */
package optimizadorga.test.algoritmo;

import static org.junit.Assert.assertEquals;
import optimizadorga.test.util.TestObjectsBuilder;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.algorithm.observerinterfaces.EraObserver;
import com.uned.optimizadorga.model.Configuration;

/**
 * @author fpb
 *
 */
public class EraTest implements EraObserver {
	private Configuration c;
	private int numeroActualizaciones;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		c = TestObjectsBuilder.buildConfiguration();		
	}


	/**
	 * Test method for {@link com.uned.optimizadorga.algorithm.Era#execute()}.
	 */
	@Test
	public void testEjecutar() {
		Era era = new Era(c);
		era.registerObserver(this);
		try {
			era.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Not enough notifications received",
				c.getMaxGens(), numeroActualizaciones);
	}

	/**
	 * Deben recibirse tantas actualizaciones como generaciones se pasan en configuracion
	 * @param resultadoParcial
	 */
	@Override
	public void updateGeneracion(Generation resultadoParcial) {
		numeroActualizaciones++;
	}

}
