/**
 * 
 */
package com.fjgarcia.optimizadorga.algoritmo.comparadores;

import java.util.Comparator;

import com.fjgarcia.optimizadorga.elementos.Cromosoma;

/**
 * Implementaci�n de un comparador de cromosomas en funci�n del coste
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class ComparadorMejorCoste implements Comparator<Cromosoma> {

	@Override
	public int compare(Cromosoma o1, Cromosoma o2) {
		if (o1.getCoste() > o2.getCoste()) {
			return 1;
		} else if (o1.getCoste() < o2.getCoste()) {
			return -1;
		} else {
			return 0;
		}
	}

}
