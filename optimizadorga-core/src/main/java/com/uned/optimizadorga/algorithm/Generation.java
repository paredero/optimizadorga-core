package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Gene;
import com.uned.optimizadorga.model.Population;

/**
 * Class in charge of the evolution of a generation
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Generation {
	private class ChromosomePosition {
		private int position;
		private Chromosome chromosome;
		public ChromosomePosition(int position, Chromosome chromosome) {
			super();
			this.position = position;
			this.chromosome = chromosome;
		}
	}
	
	private Population initialPopulation;
	private Population newPopulation;
	private Configuration configuration;

	/**
	 * Constructor
	 * @param initialPopulation The starting population
	 * @param configuration The configuration data of the execution
	 */
	public Generation(Population initialPopulation, Configuration configuration) {
		this.initialPopulation = initialPopulation;
		this.configuration = configuration;
	}

	/**
	 * Executes the evolution of a generation
	 * @throws Exception
	 */
	public Population execute() throws Exception {
		Population selectedPopulation = this.select();
		Population crossedPopulation = this.crossover(selectedPopulation);
		Population mutatedPopulation = this.mutation(crossedPopulation);
		// If elitism is configured, then the operator is executed
		if (this.configuration.getElitism()) {
			Population elitePopulation = this.elitism(mutatedPopulation);
			this.newPopulation = elitePopulation;
		} else {
			this.newPopulation = mutatedPopulation; 
		}
		return this.newPopulation;
	}
		
	/**
	 * @return The new population generated after one evolutionary step
	 */
	public Population getEvolvedPopulation() {
		return this.newPopulation;
	}

	
	/**
	 * @return the initialPopulation
	 */
	public Population getInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * Given an initial population it uses the configured selector to generate a
	 * new population
	 * @return a new population after applying the selector
	 */
	public Population select() {
		Population result = configuration.getSelector().select(initialPopulation);
		result = Population.copyPopulation(result);
		return result;
	}

	/**
	 * Applies the crossover operator on a given population
	 * @param originalPopulation
	 * @throws Exception 
	 */
	public Population crossover(Population originalPopulation) throws Exception {
		//TODO Extract behaviours to a Crossover interface
		// Selects the chromosomes to use in the crossover using the configured
		// probability
		
		Population crossedPopulation = Population.copyPopulation(originalPopulation);
		List<ChromosomePosition> selectedChromosomes = new ArrayList<ChromosomePosition>();
		int position = 0;
		for (Chromosome c:originalPopulation.getChromosomes()) {
			double random = Math.random();
			if (random < configuration.getCrossoverProbability()) {
				selectedChromosomes.add(new ChromosomePosition(position, c));
			}
			position++;
		}
		
		// it crosses the selected chromosomes pair by pair
		int i = 0;
		Iterator<ChromosomePosition> itChromosomes = selectedChromosomes.iterator();
		ChromosomePosition evenChromosome = null;
		while (itChromosomes.hasNext()) {
			ChromosomePosition oddChromosome = itChromosomes.next();
			if (i%2==0) {
				evenChromosome = oddChromosome;
			} else {
				Map<Chromosome, Chromosome> crosssedChromosomes = cross(evenChromosome.chromosome, oddChromosome.chromosome);
				crossedPopulation.getChromosomes().set(evenChromosome.position, crosssedChromosomes.get(evenChromosome.chromosome));
				crossedPopulation.getChromosomes().set(oddChromosome.position, crosssedChromosomes.get(oddChromosome.chromosome));
			}
			i++;
		}
		try {
			crossedPopulation.calculatePopulationFitness();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crossedPopulation;
	}
	
	/**
	 * TODO Maybe extract to the future Crossover interface
	 * Given two chromosomes, it performs a one point crossover operation
	 * @param evenChromosome
	 * @param oddChromosome
	 * @throws Exception 
	 */
	private Map<Chromosome, Chromosome> cross(Chromosome evenChromosome, Chromosome oddChromosome) throws Exception {
		Map<Chromosome, Chromosome> result = new HashMap<Chromosome, Chromosome>();
		Random random = new Random();
		Chromosome newEvenChromosome = new Chromosome(evenChromosome);
		Chromosome newOddChromosome = new Chromosome(oddChromosome);
		int size = evenChromosome.getGenes().size();
		int crossoverPoint = random.nextInt(size);
		
		// Starting from the crossoverPoint, it interchanges the genes of the two chrSomosomes
		while (crossoverPoint < size) {
			Gene evenGene = evenChromosome.getGenes().get(crossoverPoint);
			Gene oddGene = oddChromosome.getGenes().get(crossoverPoint); 
			Collections.replaceAll(newEvenChromosome.getGenes(), evenGene, oddGene);
			Collections.replaceAll(newOddChromosome.getGenes(), oddGene, evenGene);
			crossoverPoint++;
		}
		newOddChromosome.calculateFitness(configuration.getFitnessFunction());
		newEvenChromosome.calculateFitness(configuration.getFitnessFunction());
		result.put(oddChromosome, configuration.getChromosomeCache().getItem(newOddChromosome));
		result.put(evenChromosome, configuration.getChromosomeCache().getItem(newEvenChromosome));
		return result;
	}


	/**
	 * It implements the mutation operator
	 * Uniform mutation behaviour
	 * TODO maybe extract behaviour to a Mutator interface
	 * @param initialPopulation
	 * @throws Exception
	 */
	public Population mutation(Population initialPopulation) throws Exception {
		Population mutatedPopulation = Population.copyEmptyPopulation(initialPopulation);
		List<Chromosome> mutatedChromosomes = new ArrayList<Chromosome>(mutatedPopulation.getSize());
		for (Chromosome c:initialPopulation.getChromosomes()) {
			boolean hasMutated = false;
			Chromosome mutatedChromosome = new Chromosome(c);
			for (Gene g:mutatedChromosome.getGenes()) {
				double random = Math.random();
				if (random < configuration.getMutationProbability()) {
					hasMutated = true;
					g.generateRandomValue();
				}
			}
			// After mutation the fitness might have changed
			if (hasMutated) {
				// Only calculate fitness if it is not in the cache
				if (!configuration.getChromosomeCache().containsKey(mutatedChromosome)) {
					mutatedChromosome.calculateFitness(configuration.getFitnessFunction());	
				}
				mutatedChromosomes.add(configuration.getChromosomeCache().getItem(mutatedChromosome));
			} else {
				mutatedChromosomes.add(configuration.getChromosomeCache().getItem(c));
			}
		}
		mutatedPopulation.setChromosomes(mutatedChromosomes);
		return mutatedPopulation;
	}

	/**
	 * Implements an elitist behaviour
	 * If the best chromosome in the initial population gets carried away by worse chromosomes
	 * it replaces the  worst chromosome in the new population
	 * @param mutatedPopulation
	 */
	public Population elitism(Population mutatedPopulation) {
		Chromosome newBest = mutatedPopulation.obtainBest();		
		Chromosome initialBest = initialPopulation.obtainBest();
		Population elitePopulation = Population.copyPopulation(mutatedPopulation);
		if (newBest.getFitness() < initialBest.getFitness()) {
			Chromosome worst = mutatedPopulation.getWorst();
			elitePopulation.replaceOneChromosome(worst, initialBest);
		}
		return elitePopulation;
	}
}
