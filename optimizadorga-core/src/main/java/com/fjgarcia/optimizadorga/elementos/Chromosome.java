/**
 * 
 */
package com.fjgarcia.optimizadorga.elementos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represents a chromosome
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Chromosome {
	private double fitness;
	private List<Gene> genes;

	/**
	 * static factory method that generates a chromosome with random values
	 * @param parametros
	 * @return
	 */
	public static Chromosome generateRandomChromosome(Collection<GeneType> parametros) {
		Chromosome cromosoma = new Chromosome();
		for (GeneType tipoGen:parametros) {
			Gene genResultado = new Gene(tipoGen);
			genResultado.generateRandomValue();
			cromosoma.getGenes().add(genResultado);
		}
		return cromosoma;
	}

	
	public Chromosome(Chromosome cromosomaOrigen) {
		super();
		this.fitness = cromosomaOrigen.getFitness();
		genes = new ArrayList<Gene>();
		for (Gene g:cromosomaOrigen.getGenes()) {
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
		return "\t" + this.hashCode() +" [coste=" + fitness + ", genes=" + genes + "]";
	}

	public Chromosome() {
		super();
		this.genes = new ArrayList<Gene>();
	}

	public void calculateFitness(FitnessFunction ftnessFunction) throws Exception {
		this.fitness = ftnessFunction.evaluate(genes);
	}

}
