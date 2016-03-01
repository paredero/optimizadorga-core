/**
 * 
 */
package com.fjgarcia.optimizadorga.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

import optimizadorga.test.util.TestObjectsBuilder;

/**
 * @author fpb
 *
 */
public class PopulationTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.uned.optimizadorga.model.Population#generarPoblacionInicializada(int, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testGenerarPoblacionInicializada() throws Exception {
		Configuration c = TestObjectsBuilder.buildConfiguration();
		long startTime = System.nanoTime();
		int iterations = 100;
		long[] allElapsedTimes = new long[iterations];
		for (int i = 0; i < iterations; i++) {
			long iterationStartTime = System.nanoTime();
			Population p = Population.generateInitializedPopulation(c);
			long iterationEndTime = System.nanoTime();
			allElapsedTimes[i] = iterationEndTime - iterationStartTime;
			System.out.println(iterationEndTime - iterationStartTime);
			for (Chromosome cr:p.getChromosomes()) {
				Assert.assertNotEquals(cr.getFitness(), 0.00, 0.00);
			}
		}
		long avgTime = 0;
		for (int i = 0; i < iterations; i++) {
			avgTime += allElapsedTimes[i];
		}
		System.out.println("AvgTime: " + avgTime / (iterations));
		System.out.println("TotalTime: " + (System.nanoTime() - startTime));
	}
	
	@Test
	public void testConstructorPoblacion() {
//		Map<String, TipoGen> parametros = new HashMap<String, TipoGen>();
//		parametros.put("x1", new TipoGen("x1",-3.0, 12.1, 1));
//		parametros.put("x2", new TipoGen("x2",4.1, 5.8, 1));
//		String expresion = "21.5 + x1 * sin(4 * pi * x1) + x2 * sin(4 * pi * x2)";
//		Funcion funcionCoste = null;
//		try {
//			funcionCoste = new Funcion(expresion, parametros);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Poblacion p = Poblacion
//				.generarPoblacionInicializada(5, parametros);
//		p.setFuncionCoste(funcionCoste);
//		p.calcularCostesPoblacion();
		
//		Poblacion copia = new Poblacion(p);
//		assertNotNull("La copia no se ha creado", copia);
//		assertTrue("El tama�o de la poblacion no se ha copiado " + copia.getTamanio(),
//				copia.getTamanio() == 5);
//		assertNotNull("La lista de cromosomas no se ha creado", copia.getCromosomas());
//		assertNotNull("No se ha creado la funcion de coste de la poblacion", 
//				copia.getFuncionCoste());
//		
//		assertTrue("El objeto Copia es igual al objeto poblacion", copia != p);
//		assertTrue("La lista de cromosomas es el mismo objeto en ambas poblaciones", copia.getCromosomas() != p.getCromosomas());
//		assertTrue("La funcion de coste es distinta entre ambas poblaciones", copia.getFuncionCoste() == p.getFuncionCoste());
//		
//		// Valida los cromosomas
//		assertTrue("La funcion de coste es distinta entre ambas poblaciones", copia.getCromosomas().size() == p.getCromosomas().size());
//		int i = 0;
//		for (Cromosoma c:p.getCromosomas()) {
//			Cromosoma cCopia = copia.getCromosomas().get(i++);
//			assertTrue("El cromosoma c y el cromosoma cCopia son iguales", 
//					cCopia != c);
//			assertTrue("Costes distintos en cromosomas", c.getCoste() == cCopia.getCoste());
//			assertTrue("Lista de genes del cromosoma es el mismo objeto", c.getGenes() != cCopia.getGenes());
//			assertTrue("Lista de genes del cromosoma distintos tams", c.getGenes().size() == cCopia.getGenes().size());
//			
//			// Valido genes
//			int j = 0;
//			for (Gen g:c.getGenes()) {
//				Gen gCopia = cCopia.getGenes().get(j++);
//				assertTrue("El gen y el gen gCopia son el mismo objeto", 
//						g != gCopia);
//				assertTrue("Maximo distinto en genes", g.getTipoGen().getMaximo() == gCopia.getTipoGen().getMaximo());
//				assertTrue("Minimo distinto en genes", g.getTipoGen().getMinimo() == gCopia.getTipoGen().getMinimo());
//				assertTrue("Nombre distinto en genes", g.getNombre() == gCopia.getNombre());
//				assertTrue("Precision distinto en genes", g.getTipoGen().getPrecision() == gCopia.getTipoGen().getPrecision());
//				assertTrue("Valor distinto en genes", g.getValor() == gCopia.getValor());
//			}
//		}
	}
	

}
