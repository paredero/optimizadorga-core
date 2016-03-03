package com.uned.optimizadorga.algorithm.selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Population;

/**
 * Implementation of the selection opertor using deterministic tournament
 * selection
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public class TournamentSelector implements Selector {
	private int tournamentSize = 2;
	private Random random = new Random();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uned.optimizadorga.algorithm.selectors.Selector#select(com.uned.
	 * optimizadorga.model.Population)
	 */
	@Override
	public Population select(Population initialPopulation) {
		// 1 It creates an empty population with the same configuration
		Population selectedPopulation = Population.copyEmptyPopulation(initialPopulation);
		List<Chromosome> selectedChromosomes = new ArrayList<Chromosome>(selectedPopulation.getSize());

		Population samplePopulation = Population.copyEmptyPopulation(initialPopulation);
		samplePopulation.setSize(tournamentSize);

		// Generates random groups and takes the best of each group for the next
		// population
		while (selectedChromosomes.size() < selectedPopulation.getSize()) {
			List<Chromosome> randomSelection = this.randomSelection(initialPopulation, tournamentSize);
			samplePopulation.setChromosomes(randomSelection);
			selectedChromosomes.add(samplePopulation.obtainBest());
		}
		selectedPopulation.setChromosomes(selectedChromosomes);
		return selectedPopulation;
	}

	/** 
	 * @param basePopulation
	 * @param tournamentSize
	 * @return a list with tournamentSize chromosomes randomly selected from a
	 *         base population
	 */
	private List<Chromosome> randomSelection(Population basePopulation, int tournamentSize) {
		List<Chromosome> chromosomeList = new ArrayList<Chromosome>();
		while (chromosomeList.size() < tournamentSize) {
			int position = random.nextInt(basePopulation.getSize());
			chromosomeList.add(basePopulation.getChromosomes().get(position));
		}
		return chromosomeList;
	}


	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algorithm.selectors.Selector#getSelectorType()
	 */
	@Override
	public SelectorType getSelectorType() {
		return SelectorType.TOURNAMENT;
	}

}
