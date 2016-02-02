/**
 * 
 */
package com.uned.optimizadorga.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.uned.optimizadorga.algorithm.cache.ChromosomeCache;

/**
 * Model class representing a chromosome
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Chromosome {
	private double fitness;
	private List<Gene> genes;

	/**
	 * Static factory method to create a randomy initialized chromosome
	 * @param parameters a map with the configuration for the chromosome
	 * @return a randomly initialized chromosome
	 */
	public static Chromosome generateRandomChromosome(Map<String,GeneType> parameters) {
		Chromosome chromosome = new Chromosome();
		Iterator<String> itParameterNames = parameters.keySet().iterator();
		while (itParameterNames.hasNext()) {
			GeneType geneType = parameters.get(itParameterNames.next());
			Gene resultantGene = new Gene(geneType);
			resultantGene.generateRandomValue();
			chromosome.getGenes().add(resultantGene);
		}
		/*
		 * TODO Make some kind of ChromosomeDepositut
		if (ChromosomeCache.containsChromosome(chromosome)) {
			chromosome = ChromosomeCache.find(chromosome);
		} else {
			ChromosomeCache.put(chromosome);
		}*/
		return chromosome;
	}

	/**
	 * Constructs a chromosome from another one
	 * @param baseChromosome
	 */
	public Chromosome(Chromosome baseChromosome) {
		super();
		this.fitness = baseChromosome.getFitness();
		genes = new ArrayList<Gene>();
		for (Gene g:baseChromosome.getGenes()) {
			genes.add(new Gene(g));
		}
	}
	/**
	 * @return the genes
	 */
	public List<Gene> getGenes() {
		return genes;
	}

	/**
	 * @param genes
	 *            the genes to set
	 */
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	/**
	 * @return the coste
	 */
	public double getFitness() {
		return fitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\t" + this.hashCode() +" [fitness=" + fitness + ", genes=" + genes + "]";
	}

	public Chromosome() {
		super();
		this.genes = new ArrayList<Gene>();
	}

	public void calculateFitness(FitnessFunction fitnessFunction) throws Exception {
		this.fitness = fitnessFunction.evaluate(genes);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		return true;
	}

	
}
