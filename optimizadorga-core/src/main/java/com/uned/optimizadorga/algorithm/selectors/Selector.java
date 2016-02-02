package com.uned.optimizadorga.algorithm.selectors;

import com.uned.optimizadorga.model.Population;

/**
 * 
 * @author Francisco Javier Garcia Paredero
 * Selection operator behaviour
 */
public interface Selector {
	/**
	 * Given a population it returns a new population that contains those
	 * chromosomes selected as a result of applyint the selecton operator
	 * 
	 * @param population
	 * @return a new ppopulation that contains those chromosomes selected after applying the selector operators
	 */
	public Population select(Population population);

	public SelectorType getSelectorType();

}
