/**
 * 
 */
package com.fjgarcia.optimizadorga.algoritmo.comparadores;

import java.util.Comparator;

import com.fjgarcia.optimizadorga.elementos.Chromosome;

/**
 * Implementaci�n de un comparador de cromosomas en funci�n del coste
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class ComparadorMejorCoste implements Comparator<Chromosome> {

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		if (o1.getFitness() > o2.getFitness()) {
			return 1;
		} else if (o1.getFitness() < o2.getFitness()) {
			return -1;
		} else {
			return 0;
		}
	}

}
