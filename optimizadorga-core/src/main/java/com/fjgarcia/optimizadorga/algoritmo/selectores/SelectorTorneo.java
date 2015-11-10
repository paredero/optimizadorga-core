package com.fjgarcia.optimizadorga.algoritmo.selectores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fjgarcia.optimizadorga.elementos.Chromosome;
import com.fjgarcia.optimizadorga.elementos.Poblacion;

/**
 * Implementacion del operador selector mediante una seleccion por torneo determin�stica
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class SelectorTorneo implements Selector {
	private int numElemSeleccionados = 2;
	private Random random = new Random();
	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.selectores.Selector#seleccionar(com.uned.optimizadorga.elementos.Poblacion)
	 */
	@Override
	public Poblacion seleccionar(Poblacion poblacionInicial) {
		// 1 Inicialmente crea una poblacion vacia con la misma configuraci�n
		// sobre la que se devuelven los nuevos elementos
		Poblacion poblacionSeleccionados = Poblacion.copiarPoblacionVacia(poblacionInicial);
		List<Chromosome> cromosomasSeleccionados = new ArrayList<Chromosome>();
		
		Poblacion poblacionMuestra = Poblacion.copiarPoblacionVacia(poblacionInicial);
		poblacionMuestra.setTamanio(numElemSeleccionados);
		
		// Genera grupos aleatorios y toma el mejor de cada grupo para la nueva poblacion
		while (cromosomasSeleccionados.size()<poblacionSeleccionados.getTamanio()) {
			List<Chromosome> muestra = this.seleccionarAlAzar(poblacionInicial, numElemSeleccionados);
			poblacionMuestra.setCromosomas(muestra);
			cromosomasSeleccionados.add(poblacionMuestra.obtenerMejor());
		}
		poblacionSeleccionados.setCromosomas(cromosomasSeleccionados);
		return poblacionSeleccionados;
	}
	
	
	/**
	 * Devuelve una lista con numeroElementos Cromosomas seleccionados al azar
	 * de la poblacion inicial
	 * 
	 * @param poblacionInicial
	 * @param numeroElementos
	 * @return
	 */
	private List<Chromosome> seleccionarAlAzar(Poblacion poblacionInicial,
			int numeroElementos) {
		List<Chromosome> listaCromosomas = new ArrayList<Chromosome>();
		while (listaCromosomas.size() < numeroElementos) {
			int posicion = random.nextInt(poblacionInicial.getTamanio());
			listaCromosomas.add(poblacionInicial.getCromosomas().get(posicion));
		}
		return listaCromosomas;
	}


	/**
	 * @param numElemSeleccionados the numElemSeleccionados to set
	 */
	public void setNumElemSeleccionados(int numElemSeleccionados) {
		this.numElemSeleccionados = numElemSeleccionados;
	}


	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.selectores.Selector#getTipoSelector()
	 */
	@Override
	public String getTipoSelector() {
		return TORNEO;
	}

	
}
