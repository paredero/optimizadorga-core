package com.uned.optimizadorga.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.uned.optimizadorga.algorithm.observerinterfaces.EraObserver;
import com.uned.optimizadorga.algorithm.observerinterfaces.EraSubject;
import com.uned.optimizadorga.model.Chromosome;
import com.uned.optimizadorga.model.Configuration;
import com.uned.optimizadorga.model.Population;

/**
 * This class manages the execution of an era It implements the EraSubject
 * interface so it can update observers about its progress 
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Era implements EraSubject {
	private Configuration configuration;
	private Population initialPopulation;
	private List<EraObserver> observers;
	
	// Keeps the population generated as a result of the evolution in the
	// computation
	private List<Population> evolvedPopulations;
	private Chromosome bestIndividual;
	

	
	public Era(Configuration configuration) {
		super();
		this.configuration = configuration;
		observers = new ArrayList<EraObserver>();
	}


	/**
	 * Performs the execution of the evolution
	 * @throws Exception
	 */
	public void execute() throws Exception {
		if (!Thread.currentThread().isInterrupted()) {
			this.initializePopulation();
		}
		if (!Thread.currentThread().isInterrupted()) {
			this.executeLoop();
		}
	}
	
	/**
	 * Creates the initial population
	 * @throws Exception
	 */
	private void initializePopulation() throws Exception {
		this.initialPopulation = Population.generateInitializedPopulation(configuration);
		this.evolvedPopulations = new ArrayList<Population>(configuration.getMaxGens());
		this.evolvedPopulations.add(initialPopulation);
	}
	
	private void executeLoop() throws Exception {
		int currentGeneration = 0;
		while (!Thread.currentThread().isInterrupted() && currentGeneration < configuration.getMaxGens()) {
			Generation generation = new Generation(initialPopulation,
					configuration);
			currentGeneration++;
			generation.execute();
			// Adds the result of the generation computing which is a new population
			this.evolvedPopulations.add(generation.getEvolvedPopulation());
			
			// The initial population of next generation will be the one obtained in the
			// last iteration 
			this.initialPopulation = generation.getEvolvedPopulation();
			this.notifyGeneracion(generation);
		}
	}

	/**
	 * @return The best chromosome currently obtained
	 */
	public Chromosome obtainBest() {
		bestIndividual = this.evolvedPopulations.get(
				this.evolvedPopulations.size() - 1).obtainBest();
		return bestIndividual;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.interfaces.EraSubject#registerObserver(com.uned.optimizadorga.algoritmo.interfaces.EraObserver)
	 */
	@Override
	public void registerObserver(EraObserver observer) {
		observers.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.interfaces.EraSubject#notifyGeneracion(com.uned.optimizadorga.algoritmo.Generacion)
	 */
	@Override
	public void notifyGeneracion(Generation generacionProcesada) {
		for (EraObserver o:this.observers) {
			o.updateGeneracion(generacionProcesada);
		}
	}


	/**
	 * @return the populations generated during the execution of
	 * the generations in an era
	 */
	public List<Population> getEvolvedPopulations() {
		return this.evolvedPopulations;
	}
}
