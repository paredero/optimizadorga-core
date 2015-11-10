package com.fjgarcia.optimizadorga.algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.fjgarcia.optimizadorga.elementos.Configuracion;
import com.fjgarcia.optimizadorga.elementos.Chromosome;
import com.fjgarcia.optimizadorga.elementos.Gene;
import com.fjgarcia.optimizadorga.elementos.Poblacion;

/**
 * Clase encargada de la evoluci�n de una generaci�n
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Generacion {
	private Poblacion poblacionInicial;
	private Poblacion nuevaPoblacion;
	private Configuracion configuracion;

	public Generacion(Poblacion poblacion, Configuracion configuracion) {
		this.poblacionInicial = poblacion;
		this.configuracion = configuracion;
	}

	/**
	 * Ejecuci�n de la evoluci�n de una generaci�n
	 * @throws Exception
	 */
	public void ejecutar() throws Exception {
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
	public Poblacion getNuevaPoblacion() {
		return this.nuevaPoblacion;
	}

	
	/**
	 * @return the poblacionInical
	 */
	public Poblacion getPoblacionInicial() {
		return poblacionInicial;
	}

	/**
	 * @return the configuracion
	 */
	public Configuracion getConfiguracion() {
		return configuracion;
	}

	/**
	 * Dada una poblacion inicial, genera una nueva poblacion aplicando el selector
	 * @return la nueva poblacion aplicando el selector
	 */
	private Poblacion seleccionar() {
		Poblacion resultado = configuracion.getSelector().seleccionar(poblacionInicial);
		resultado = Poblacion.copiarPoblacion(resultado);
		return resultado;
	}

	/**
	 * @param nuevaPoblacion
	 */
	private void operadorCruce(Poblacion poblacion) {
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
			Gene genPar = cPar.getGenes().get(i);
			Gene genImpar = cImpar.getGenes().get(i); 
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
	private void operadorMutacion(Poblacion poblacion) throws Exception {
		for (Chromosome c:poblacion.getCromosomas()) {
			for (Gene g:c.getGenes()) {
				double random = Math.random();
				if (random < configuracion.getProbabilidadMutacion()) {
					g.generateRandomValue();
				}
			}
			c.calculateFitness(configuracion.getFuncionCoste());
		}
	}

	/**
	 * @param nuevaPoblacion
	 */
	private void operadorElitismo(Poblacion nuevaPoblacion) {
		Chromosome nuevoMejor = nuevaPoblacion.obtenerMejor();		
		Chromosome mejorPoblacionInicial = poblacionInicial.obtenerMejor();
		if (nuevoMejor.getFitness() < mejorPoblacionInicial.getFitness()) {
			Chromosome peor = nuevaPoblacion.obtenerPeor();
			nuevaPoblacion.sustituirCromosoma(peor, mejorPoblacionInicial);
		}
	}
}
