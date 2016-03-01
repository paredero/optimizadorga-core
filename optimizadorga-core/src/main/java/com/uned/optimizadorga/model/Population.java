package com.uned.optimizadorga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.uned.optimizadorga.algorithm.cache.ChromosomeCache;
import com.uned.optimizadorga.algorithm.comparators.BestFitnessComparator;

/**
 * Model class that represents the population
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Population {	
	private List<Chromosome> chromosomes;
	private int size;
	private FitnessFunction fitnessFunction;
	private Chromosome bestChromosome;
	
	/**
	 * Static factory method that creates a randomly initializedPopulation
	 * @param configuration
	 * @return A randomly initialized population
	 * @throws Exception 
	 */
	public static Population generateInitializedPopulation(Configuration configuration) throws Exception {
		ChromosomeCache chromosomeCache = configuration.getChromosomeCache();
		Population population = new Population();
		population.setSize(configuration.getPopulationSize());
		
		for (int i = 0; i < configuration.getPopulationSize(); i++) {
			Chromosome chromosome = Chromosome.generateRandomChromosome(configuration
					.getParameters());
			population.getChromosomes().add(chromosomeCache.getItem(chromosome));			
		}
		population.setFitnessFunction(configuration.getFitnessFunction());
		population.calculatePopulationFitness();
		return population;
	}

	
	private Population() {
		super();
		this.chromosomes = new ArrayList<Chromosome>();
	}
	
	

	public List<Chromosome> getChromosomes() {
		return chromosomes;
	}


	public void setChromosomes(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}


	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}


	public Chromosome getBestChromosome() {
		return bestChromosome;
	}


	public void setBestChromosome(Chromosome bestChromosome) {
		this.bestChromosome = bestChromosome;
	}


	/**
	 * Calculates the fitness for each individual in the population
	 * @throws Exception 
	 */
	public void calculatePopulationFitness() throws Exception {
		this.getChromosomes().parallelStream().filter(new Predicate<Chromosome>() {
			@Override
			public boolean test(Chromosome t) {
				return t.getFitness() == 0.0;
			}
		}).forEach(individuo -> {
			try {
				individuo.calculateFitness(this.fitnessFunction);
			} catch (Exception e) {
			}
		});
		/*
		for (Chromosome individuo : this.getChromosomes()) {
			individuo.calculateFitness(this.fitnessFunction);
		}*/
	}

	/**
	 * @return The best chromosome in the population
	 */
	public Chromosome obtainBest() {
		bestChromosome = Collections
				.max(chromosomes, new BestFitnessComparator());
				return bestChromosome;
		
	}

	/**
	 * @return the worst individual in the population
	 */
	public Chromosome getWorst() {
		Chromosome worstChromosome = Collections.min(chromosomes,
				new BestFitnessComparator());
		return worstChromosome;
	}
	
	/**
	 * Creates a population whithout any individuals
	 * copies the fitness function and the size
	 * @param population
	 * @return an empty but configurated population
	 */
	public static Population copyEmptyPopulation(Population population) {
		Population copy = new Population();
		copy.setFitnessFunction(population.getFitnessFunction());
		copy.setSize(population.getSize());
		return copy;
	}

	/**
	 * Copies a population, chromosomes included
	 * @param originalPopulation
	 * @return
	 */
	public static Population copyPopulation(Population originalPopulation) {
		Population copy = new Population();
		copy.setFitnessFunction(originalPopulation.getFitnessFunction());
		copy.setSize(originalPopulation.getSize());
		List<Chromosome> chromosomeList = new ArrayList<Chromosome>();
		for (Chromosome c:originalPopulation.getChromosomes()) {
			Chromosome newChromosome = new Chromosome(c);
			chromosomeList.add(newChromosome);
		}
		copy.setChromosomes(chromosomeList);
		return copy;
	}
	
	

	
	/**
	 * Sustituye en la poblacion un cromosoma por otro
	 * @param antiguo
	 * @param nuevo
	 */
	public void replaceChromosome(Chromosome antiguo,
			Chromosome nuevo) {
		Collections.replaceAll(this.getChromosomes(), antiguo, new Chromosome(nuevo));
		bestChromosome = null;
	}


	


}
