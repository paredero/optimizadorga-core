package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
		newPopulation = this.select();
		newPopulation = crossover(newPopulation);
		newPopulation = this.mutation(newPopulation);
		// If elitism is configured, then the operator is executed
		if (this.configuration.getElitism()) {
			newPopulation = this.elitism(newPopulation);
		}
		return newPopulation;
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
		return result;
	}

	/**
	 * Applies the crossover operator on a given population
	 * @param population
	 * @throws Exception 
	 */
	public Population crossover(Population population) throws Exception {
		//TODO Extract behaviours to a Crossover interface
		// Selects the chromosomes to use in the crossover using the configured
		// probability
		List<Chromosome> selectedChromosomes = new ArrayList<Chromosome>();
		for (Chromosome c:population.getChromosomes()) {
			double random = Math.random();
			if (random < configuration.getCrossoverProbability()) {
				selectedChromosomes.add(c);
			}
		}
		
		// it crosses the selected chromosomes pair by pair
		int i = 0;
		Iterator<Chromosome> itChromosomes = selectedChromosomes.iterator();
		Chromosome evenChromosome = null;
		while (itChromosomes.hasNext()) {
			Chromosome oddChromosome = itChromosomes.next();
			if (i%2==0) {
				evenChromosome = oddChromosome;
			} else {
				cross(evenChromosome, oddChromosome);
			}
			i++;
		}
		population.calculatePopulationFitness();
		return population;
	}
	
	/**
	 * TODO Maybe extract to the future Crossover interface
	 * Given two chromosomes, it performs a one point crossover operation
	 * @param evenChromosome
	 * @param oddChromosome
	 */
	private void cross(Chromosome evenChromosome, Chromosome oddChromosome) {
		Random random = new Random();
		int size = evenChromosome.getGenes().size();
		int crossoverPoint = random.nextInt(size);
		
		// Starting from the crossoverPoint, it interchanges the genes of the two chrSomosomes
		while (crossoverPoint < size) {
			Gene evenGene = evenChromosome.getGenes().get(crossoverPoint);
			Gene oddGene = oddChromosome.getGenes().get(crossoverPoint); 
			Collections.replaceAll(evenChromosome.getGenes(), evenGene, oddGene);
			Collections.replaceAll(oddChromosome.getGenes(), oddGene, evenGene);
			crossoverPoint++;
		}
	}


	/**
	 * It implements the mutation operator
	 * Uniform mutation behaviour
	 * TODO maybe extract behaviour to a Mutator interface
	 * @param population
	 * @return 
	 * @throws Exception
	 */
	public Population mutation(Population population) throws Exception {
		for (Chromosome c:population.getChromosomes()) {
			boolean hasMutated = false;
			for (Gene g:c.getGenes()) {
				double random = Math.random();
				if (random < configuration.getMutationProbability()) {
					hasMutated = true;
					g.generateRandomValue();
				}
			}
			// After mutation the fitness might have changed
			if (hasMutated) {
				c.calculateFitness(configuration.getFitnessFunction());
			}
		}
		return population;
	}

	/**
	 * Implements an elitist behaviour
	 * If the best chromosome in the initial population gets carried away by worse chromosomes
	 * it replaces the  worst chromosome in the new population
	 * @param newPopulation
	 * @return 
	 */
	public Population elitism(Population newPopulation) {
		Chromosome newBest = newPopulation.obtainBest();		
		Chromosome initialBest = initialPopulation.obtainBest();
		if (newBest.getFitness() < initialBest.getFitness()) {
			Chromosome worst = newPopulation.getWorst();
			newPopulation.replaceChromosome(worst, initialBest);
		}
		return newPopulation;
	}
}
