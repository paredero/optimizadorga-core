package optimizadorga.test.util;

import java.util.HashMap;
import java.util.Map;

import com.uned.optimizadorga.algorithm.selectors.SelectorType;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.FitnessFunction;
import com.uned.optimizadorga.model.GeneType;

/**
 * This class will manage the object creation for my tests
 * 
 * @author javier
 *
 */
public class TestObjectsBuilder {
	private static String functionToTest = "21.5 + x1 * sin(4 * pi * x1) + x2 * sin(4 * pi * x2)";
	private static int erasToTest = 100;
	private static int generationsToTest = 100;
	private static int populationSizeToTest = 100;
	private static double crossoverProbability = 0.5;
	private static double mutationProbability = 0.5;
	private static boolean testWithElitism = true;
	private static SelectorType selectorTypeToTest = SelectorType.ROULETTE;

	public static Map<String, GeneType> buildGenes() {
		Map<String, GeneType> genesParametro = new HashMap<String, GeneType>();
		genesParametro.put("x1", new GeneType("x1", -3.0, 12.1, 1));
		genesParametro.put("x2", new GeneType("x2", 4.1, 5.8, 1));
		return genesParametro;
	}

	public static String buildStringExpression() {
		return functionToTest;
	}

	public static FitnessFunction buildFitnessFunction() throws Exception {
		Map<String, GeneType> genes = buildGenes();
		String expresion = buildStringExpression();
		return new FitnessFunction(expresion, genes);
	}

	public static Configuration buildConfiguration() throws Exception {
		return Configuration.createConfiguration(erasToTest, generationsToTest, buildFitnessFunction(), buildGenes(),
				populationSizeToTest, crossoverProbability, mutationProbability, testWithElitism, selectorTypeToTest);
	}

}
