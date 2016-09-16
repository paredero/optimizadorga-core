/**
 * 
 */
package com.uned.optimizadorga.algorithm.selectors;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Population;

/**
 * Selection implementation based in a roulette method
 * 
 * @author Francisco Javier Garcia Paredero
 */
public class RouletteSelector implements Selector {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.selectors.Selector#select(com.uned.
	 * optimizadorga.elementos.Population)
	 */
	@Override
	public Population select(Population initialPopulation) {
		Population selectedElements = Population.copyEmptyPopulation(initialPopulation);

		double offset = this.calculateOffset(initialPopulation);
		// 1.- Calculates the totalAmount of the fitness values for all the
		// chromosomes in the population
		double totalFitness = 0;
		for (Chromosome c : initialPopulation.getChromosomes()) {
			totalFitness += c.getFitness() + offset;
		}

		// 2 and 3. Calculates the selection probability for each chromosome and
		// the accumulated probability
		double[] accumulatedProbability = new double[initialPopulation.getSize()];
		double totalProbabilities = 0;
		int i = 0;
		for (Chromosome c : initialPopulation.getChromosomes()) {
			double elementProbability = (c.getFitness() + offset) / totalFitness;
			totalProbabilities += elementProbability;
			accumulatedProbability[i] = totalProbabilities;
			i++;
		}

		// 4. Will roll the roulette "size" times
		for (int j = 0; j < initialPopulation.getSize(); j++) {
			rouletteRoll(initialPopulation, selectedElements, accumulatedProbability);
		}
		return selectedElements;
	}

	/**
	 * If the population contains fitness values minor than 0, it will obtain an
	 * offset so it can normalize it. The offset will be enough that minimum
	 * value will become 0
	 * 
	 * @param initialPopulation
	 * @return
	 */
	private double calculateOffset(Population initialPopulation) {
		double offset = 0;
		double min = initialPopulation.getWorst().getFitness();
		if (min < 0) {
			offset = (-1 * min);
		}
		return offset;
	}

	/**
	 * Performs a roll in the roulette and adds the selected element to the
	 * selected population
	 * 
	 * @param initialPopulation
	 * @param selectedPopulation
	 * @param accumulatedProbabilities
	 */
	private void rouletteRoll(Population initialPopulation, Population selectedPopulation,
			double[] accumulatedProbabilities) {
		// 4a. Generates a random number
		double rouletteValue = Math.random();
		// 4b. Selects the chromosome based in this random number
		if (rouletteValue < accumulatedProbabilities[0]) {
			selectedPopulation.getChromosomes().add(initialPopulation.getChromosomes().get(0));
		} else {
			int k = 1;
			try {
				boolean stop = false;
				while (!stop && k < accumulatedProbabilities.length - 1) {
					double probab = accumulatedProbabilities[k];
					if (rouletteValue <= probab) {
						stop = true;
					} else {
						k++;
					}
				}
				selectedPopulation.getChromosomes().add(initialPopulation.getChromosomes().get(k));
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw e;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.selectors.Selector#getTipoSelector()
	 */
	@Override
	public SelectorType getSelectorType() {
		return SelectorType.ROULETTE;
	}

}
