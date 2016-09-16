package com.uned.optimizadorga.model;

import java.util.Map;

import com.uned.optimizadorga.algorithm.cache.ChromosomeCache;
import com.uned.optimizadorga.algorithm.selectors.Selector;
import com.uned.optimizadorga.algorithm.selectors.SelectorFactory;
import com.uned.optimizadorga.algorithm.selectors.SelectorType;

/**
 * Container class for the algorithm configuration
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Configuration {
	private int maxEras;
	private int maxGens;
	private FitnessFunction fitnessFunction;
	private Map<String, GeneType> parameters;
	private int populationSize;
	private double crossoverProbability;
	private double mutationProbability;
	private Selector selector;
	private boolean elitism;
	private ChromosomeCache chromosomeCache;

	private Configuration() {
		super();
	}
	
	/**
	 * Static factory method
	 * 
	 * @param maxEras
	 * @param maxGenerations
	 * @param fitnessFunction
	 * @param parameters
	 * @param populationSize
	 * @param crossoverProbability
	 * @param mutationProbability
	 * @param useElitism
	 * @param selectorRuleta
	 * @param selectorTorneo
	 * @return
	 */
	public static Configuration createConfiguration(Integer maxEras, Integer maxGenerations, FitnessFunction fitnessFunction,
			Map<String, GeneType> parameters, Integer populationSize, Double crossoverProbability,
			Double mutationProbability, boolean useElitism, SelectorType selectorType) {
		Configuration instancia = new Configuration();
		instancia.maxEras = maxEras;
		instancia.maxGens = maxGenerations;
		instancia.fitnessFunction = fitnessFunction;
		instancia.parameters = parameters;
		instancia.populationSize = populationSize;
		instancia.crossoverProbability = crossoverProbability;
		instancia.mutationProbability = mutationProbability;
		instancia.elitism = useElitism;
		instancia.selector = SelectorFactory.getInstance(selectorType);
		instancia.chromosomeCache = new ChromosomeCache();
		return instancia;
	}

	

	// ************************************************************************
	//	GETTER & SETTER METHODS
	// ************************************************************************
	
	public int getMaxEras() {
		return maxEras;
	}

	public void setMaxEras(int maxEras) {
		this.maxEras = maxEras;
	}

	public int getMaxGens() {
		return maxGens;
	}

	public void setMaxGens(int maxGens) {
		this.maxGens = maxGens;
	}

	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	public Map<String, GeneType> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, GeneType> parameters) {
		this.parameters = parameters;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public boolean getElitism() {
		return elitism;
	}

	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}

}
