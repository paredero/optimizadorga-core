package optimizadorga.test.algoritmo;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.Algorithm;
import com.uned.optimizadorga.algorithm.ConcurrentAlgorithm;
import com.uned.optimizadorga.algorithm.Era;
import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.algorithm.SynchronousAlgorithm;
import com.uned.optimizadorga.algorithm.observerinterfaces.AlgorithmObserver;
import com.uned.optimizadorga.algorithm.selectors.SelectorType;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.FitnessFunction;
import com.uned.optimizadorga.model.GeneType;

public class AlgoritmoTest implements AlgorithmObserver {

	private static final int ERAS_TO_RUN = 4;
	private static final int GENERATIONS_TO_RUN = 1000;
	private FitnessFunction fitnessFunction;
	private Configuration c;
	private Algorithm algorithm;
	private int endNotifications;
	private int generationNotifications;
	private int eraNotifications;

	@Before
	public void setUp() throws Exception {
		String expression = "21.5 + x1 * sin(4 * pi * x1) + x2 * sin(4 * pi * x2)";
		
		Map<String, GeneType> geneTypeMap = new HashMap<String, GeneType>();
		geneTypeMap.put("x1", new GeneType("x1", -3.0, 12.1, 1));
		geneTypeMap.put("x2", new GeneType("x2", 4.1, 5.8, 1));
		fitnessFunction = new FitnessFunction(expression,geneTypeMap);
		c = Configuration.createConfiguration(ERAS_TO_RUN, GENERATIONS_TO_RUN,
				fitnessFunction, geneTypeMap, 100, 0.5, 0.5, false, SelectorType.ROULETTE);

		
	}


	@Test
	public void testSynchronousRun() {
		algorithm = new SynchronousAlgorithm(c);
		algorithm.registerObserver(this);
		algorithm.run();
		assertEquals("Number of era notifications doesnt match", eraNotifications,ERAS_TO_RUN);
		assertEquals("Number of generation notifications doesnt match", generationNotifications,ERAS_TO_RUN*GENERATIONS_TO_RUN);
		assertEquals("Unexpected number of end notifications", endNotifications,1);
	}

	@Test
	public void testAsynchronousRun() throws InterruptedException {
		algorithm = new ConcurrentAlgorithm(c);
		algorithm.registerObserver(this);
		Thread threadAlgorithm = new Thread(algorithm);
		threadAlgorithm.start();		
		threadAlgorithm.join();
		assertEquals("Number of era notifications doesnt match", eraNotifications,ERAS_TO_RUN);
		assertEquals("Number of generation notifications doesnt match", generationNotifications,ERAS_TO_RUN*GENERATIONS_TO_RUN);
		assertEquals("Unexpected number of end notifications", endNotifications,1);
	}
	
	@Override
	public void updateEndEraExecution(Era resultadoParcial) {
		eraNotifications++;
	}


	@Override
	public void updateEndGenerationExecution(Generation resultadoParcial) {
		generationNotifications++;
	}


	@Override
	public void updateEnd() {
		endNotifications++;
	}


	@Override
	public void updateError(Exception e) {
		
	}

}
