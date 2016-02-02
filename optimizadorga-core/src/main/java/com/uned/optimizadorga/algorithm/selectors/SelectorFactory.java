package com.uned.optimizadorga.algorithm.selectors;
/**
 * Factory for selection algorithms
 * @author javier
 *
 */
public class SelectorFactory {
	/**
	 * Creates a selector
	 * @param selectorType
	 * @return
	 */
	public static Selector getInstance(SelectorType selectorType) {
		switch (selectorType) {
		case TOURNAMENT:
			return new TournamentSelector();
		case ROULETTE:
			return new RouletteSelector();
		default:
			return new RouletteSelector();
		}
	}
}
