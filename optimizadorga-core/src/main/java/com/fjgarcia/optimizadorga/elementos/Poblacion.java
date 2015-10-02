package com.fjgarcia.optimizadorga.elementos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fjgarcia.optimizadorga.algoritmo.comparadores.ComparadorMejorCoste;

/**
 * Representa una poblaci�n
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Poblacion {	
	private List<Cromosoma> cromosomas;
	private int tamanio;
	private Funcion funcionCoste;

	private Cromosoma mejorCromosoma;
	
	/**
	 * @param configuracion
	 * @return una poblacion inicializada con cromosomas aleatorios
	 * @throws Exception 
	 */
	public static Poblacion generarPoblacionInicializada(Configuracion configuracion) throws Exception {
		Poblacion poblacion = new Poblacion();
		poblacion.setTamanio(configuracion.getTamanioPoblacion());
		
		for (int i = 0; i < configuracion.getTamanioPoblacion(); i++) {
			poblacion.getCromosomas().add(
					Cromosoma.generarCromosomaAleatorio(configuracion
							.getParametros()));			
		}
		poblacion.setFuncionCoste(configuracion.getFuncionCoste());
		poblacion.calcularCostesPoblacion();
		return poblacion;
	}

	
	public Poblacion() {
		super();
		this.cromosomas = new ArrayList<Cromosoma>();
	}

	/**
	 * @return the cromosomas
	 */
	public List<Cromosoma> getCromosomas() {
		return cromosomas;
	}

	/**
	 * @param cromosomas
	 *            the cromosomas to set
	 */
	public void setCromosomas(List<Cromosoma> cromosomas) {
		this.cromosomas = cromosomas;
	}

	/**
	 * @return the tamanio
	 */
	public int getTamanio() {
		return tamanio;
	}

	/**
	 * @param tamanio
	 *            the tamanio to set
	 */
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Poblacion " + this.hashCode() + "(" + tamanio + ")"
				+ cromosomas + "]";
	}

	public void setFuncionCoste(Funcion expresion) {
		this.funcionCoste = expresion;
	}

	/**
	 * @return the funcionCoste
	 */
	public Funcion getFuncionCoste() {
		return funcionCoste;
	}

	/**
	 * Calcula el coste para la cada individuo de la poblaci�n
	 * @throws Exception 
	 */
	public void calcularCostesPoblacion() throws Exception {
//		log.debug("***********calcula costes de la poblacion " + this.hashCode());
		for (Cromosoma individuo : this.getCromosomas()) {
			individuo.calcularCoste(this.funcionCoste);
		}
//		log.debug("***********Fin del calculo costes de la poblacion " + this);
	}

	/**
	 * @return el mejor individuo de la poblaci�n
	 */
	public Cromosoma obtenerMejor() {
		mejorCromosoma = Collections
				.max(cromosomas, new ComparadorMejorCoste());

		return mejorCromosoma;
	}

	/**
	 * @return el peor individuo de la poblacion
	 */
	public Cromosoma obtenerPeor() {
		Cromosoma peorCromosoma = Collections.min(cromosomas,
				new ComparadorMejorCoste());
		return peorCromosoma;
	}
	
	/**
	 * Crea una poblacion sin cromosomas
	 * copia la funcion de coste y el tama�o
	 * @param poblacion
	 * @return una poblacion vacia
	 */
	public static Poblacion copiarPoblacionVacia(Poblacion poblacion) {
		Poblacion copia = new Poblacion();
		copia.setFuncionCoste(poblacion.getFuncionCoste());
		copia.setTamanio(poblacion.getTamanio());
		return copia;
	}

	public static Poblacion copiarPoblacion(Poblacion origen) {
		Poblacion copia = new Poblacion();
		copia.setFuncionCoste(origen.getFuncionCoste());
		copia.setTamanio(origen.getTamanio());
		List<Cromosoma> listaCromosomas = new ArrayList<Cromosoma>();
		for (Cromosoma c:origen.getCromosomas()) {
			Cromosoma nuevo = new Cromosoma(c);
			listaCromosomas.add(nuevo);
		}
		copia.setCromosomas(listaCromosomas);
		return copia;
	}
	
	/**
	 * @return la media de la funcion de coste para los cromosomas de la poblacion
	 */
	public double calcularMediaCoste() {
		double sumaCostes = 0.0;
		for(Cromosoma c: this.cromosomas) {
			sumaCostes+=c.getCoste();
		}
		return sumaCostes/this.tamanio;
	}

	public double calcularDesviacionTipica() {
		double media = this.calcularMediaCoste();
		double cuadradosAcumulados = 0;
		for(Cromosoma c: this.cromosomas) {
			double diferencia = c.getCoste() - media;
			cuadradosAcumulados += (diferencia*diferencia);
		}
		return Math.sqrt(cuadradosAcumulados/this.tamanio);
	}

	
	/**
	 * Sustituye en la poblacion un cromosoma por otro
	 * @param antiguo
	 * @param nuevo
	 */
	public void sustituirCromosoma(Cromosoma antiguo,
			Cromosoma nuevo) {
		Collections.replaceAll(this.getCromosomas(), antiguo, new Cromosoma(nuevo));
		mejorCromosoma = null;
	}


	


}
