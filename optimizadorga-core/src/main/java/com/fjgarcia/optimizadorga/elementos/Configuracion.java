package com.fjgarcia.optimizadorga.elementos;

import java.util.Map;

import com.fjgarcia.optimizadorga.algoritmo.selectores.Selector;
import com.fjgarcia.optimizadorga.algoritmo.selectores.SelectorRuleta;
import com.fjgarcia.optimizadorga.algoritmo.selectores.SelectorTorneo;

/**
 * Clase contenedora de la configuraci�n del algoritmo
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Configuracion {
	private static Configuracion instancia = new Configuracion();
	private int maxEras;
	private int maxGens;
	private FitnessFunction funcionCoste;
	private Map<String, GeneType> parametros;
	private int tamanioPoblacion;
	private double probabilidadCruce;
	private double probabilidadMutacion;
	private Selector selector;
	private boolean elitismo;

	private Configuracion() {
		super();
	}
	
	
	public Map<String, GeneType> getParametros() {
		return this.parametros;
	}

	public int getTamanioPoblacion() {
		return this.tamanioPoblacion;
	}

	public FitnessFunction getFuncionCoste() {
		return this.funcionCoste;
	}

	public int getMaxGens() {
		return this.maxGens;
	}

	public Selector getSelector() {
		return this.selector;
	}

	public double getProbabilidadCruce() {
		return this.probabilidadCruce;
	}

	public double getProbabilidadMutacion() {
		return this.probabilidadMutacion;
	}

	public int getMaxEras() {
		return this.maxEras;
	}

	
	/**
	 * @return the elitismo
	 */
	public boolean getElitismo() {
		return this.elitismo;
	}


	public static Configuracion crearConfiguracion(Integer maxEras,
			Integer maxGens, FitnessFunction funcionCoste, Map<String, GeneType> parametros,
			Integer tamanioPoblacion, Double probabilidadCruce, Double probabilidadMutacion, boolean usarElitismo, boolean selectorRuleta, boolean selectorTorneo) {
		instancia.maxEras = maxEras;
		instancia.maxGens = maxGens;
		instancia.funcionCoste = funcionCoste;
		instancia.parametros = parametros;
		instancia.tamanioPoblacion = tamanioPoblacion;
		instancia.probabilidadCruce = probabilidadCruce;
		instancia.probabilidadMutacion = probabilidadMutacion;
		instancia.elitismo = usarElitismo;
		if (selectorRuleta) {
			instancia.selector = new SelectorRuleta();
		} else if (selectorTorneo) {
			instancia.selector = new SelectorTorneo();
		}
		
		
		return instancia;
	}

}
