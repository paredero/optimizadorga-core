/**
 * 
 */
package com.uned.optimizadorga.algorithm.comparators;

import java.util.Comparator;

import com.uned.optimizadorga.model.Chromosome;

/**
 * Implementation of a chromosome comparator regarding fitness
 * @author Francisco Javier GarciSa Paredero
 *
 */
public class BestFitnessComparator implements Comparator<Chromosome> {

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
