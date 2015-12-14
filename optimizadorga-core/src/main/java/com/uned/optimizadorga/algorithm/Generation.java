package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.uned.optimizadorga.elementos.Configuration;
import com.uned.optimizadorga.elementos.Chromosome;
import com.uned.optimizadorga.elementos.Gen;
import com.uned.optimizadorga.elementos.Population;

/**
 * Clase encargada de la evoluci�n de una generaci�n
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Generation {
	private Population poblacionInicial;
	private Population nuevaPoblacion;
	private Configuration configuracion;

	public Generation(Population poblacion, Configuration configuracion) {
		this.poblacionInicial = poblacion;
		this.configuracion = configuracion;
	}

	/**
	 * Ejecuci�n de la evoluci�n de una generaci�n
	 * @throws Exception
	 */
	public void execute() throws Exception {
		nuevaPoblacion = this.seleccionar();
		operadorCruce(nuevaPoblacion);
		operadorMutacion(nuevaPoblacion);
		if (this.configuracion.getElitismo()) {
			operadorElitismo(nuevaPoblacion);
		}
		
	}
	
	
	
	/**
	 * @return La nueva poblaci�n surgida tras un paso evolutivo
	 */
	public Population getEvolvedPopulation() {
		return this.nuevaPoblacion;
	}

	
	/**
	 * @return the poblacionInical
	 */
	public Population getPoblacionInicial() {
		return poblacionInicial;
	}

	/**
	 * @return the configuracion
	 */
	public Configuration getConfiguracion() {
		return configuracion;
	}

	/**
	 * Dada una poblacion inicial, genera una nueva poblacion aplicando el selector
	 * @return la nueva poblacion aplicando el selector
	 */
	private Population seleccionar() {
		Population resultado = configuracion.getSelector().seleccionar(poblacionInicial);
		resultado = Population.copiarPoblacion(resultado);
		return resultado;
	}

	/**
	 * @param nuevaPoblacion
	 */
	private void operadorCruce(Population poblacion) {
		List<Chromosome> cromosomasSeleccionados = new ArrayList<Chromosome>();
		for (Chromosome c:poblacion.getCromosomas()) {
			double random = Math.random();
			if (random < configuracion.getProbabilidadCruce()) {
				cromosomasSeleccionados.add(c);
			}
		}
		int i = 0;
		Iterator<Chromosome> itCromosomas = cromosomasSeleccionados.iterator();
		Chromosome cPar = null;
		while (itCromosomas.hasNext()) {
			Chromosome cImpar = itCromosomas.next();
			if (i%2==0) {
//				Si es par
				cPar = cImpar;
			} else {
				cruzar(cPar, cImpar);
			}
			i++;
		}
		try {
			poblacion.calcularCostesPoblacion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param nuevaPoblacion
	 */
	
	private void cruzar(Chromosome cPar, Chromosome cImpar) {
		Random random = new Random();
		int max = cPar.getGenes().size();
		int posCruce = random.nextInt(max);
		int i = posCruce;
		
		while (i < max) {
			Gen genPar = cPar.getGenes().get(i);
			Gen genImpar = cImpar.getGenes().get(i); 
			Collections.replaceAll(cPar.getGenes(), genPar, genImpar);
			Collections.replaceAll(cImpar.getGenes(), genImpar, genPar);
			i++;
		}
//		log.debug("Resultado del cruce " + cPar + cImpar);
	}

	/**
	 * @param nuevaPoblacion
	 * @throws Exception 
	 */	
	private void operadorMutacion(Population poblacion) throws Exception {
		for (Chromosome c:poblacion.getCromosomas()) {
			for (Gen g:c.getGenes()) {
				double random = Math.random();
				if (random < configuracion.getProbabilidadMutacion()) {
					g.generarValorAleatorio();
				}
			}
			c.calcularCoste(configuracion.getFuncionCoste());
		}
	}

	/**
	 * @param nuevaPoblacion
	 */
	private void operadorElitismo(Population nuevaPoblacion) {
		Chromosome nuevoMejor = nuevaPoblacion.obtainFittest();		
		Chromosome mejorPoblacionInicial = poblacionInicial.obtainFittest();
		if (nuevoMejor.getCoste() < mejorPoblacionInicial.getCoste()) {
			Chromosome peor = nuevaPoblacion.obtenerPeor();
			nuevaPoblacion.sustituirCromosoma(peor, mejorPoblacionInicial);
		}
	}
}
