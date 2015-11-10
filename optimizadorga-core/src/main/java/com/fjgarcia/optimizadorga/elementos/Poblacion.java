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
	private List<Chromosome> cromosomas;
	private int tamanio;
	private FitnessFunction funcionCoste;

	private Chromosome mejorCromosoma;
	
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
					Chromosome.generateRandomChromosome(configuracion
							.getParametros().values()));			
		}
		poblacion.setFuncionCoste(configuracion.getFuncionCoste());
		poblacion.calcularCostesPoblacion();
		return poblacion;
	}

	
	public Poblacion() {
		super();
		this.cromosomas = new ArrayList<Chromosome>();
	}

	/**
	 * @return the cromosomas
	 */
	public List<Chromosome> getCromosomas() {
		return cromosomas;
	}

	/**
	 * @param cromosomas
	 *            the cromosomas to set
	 */
	public void setCromosomas(List<Chromosome> cromosomas) {
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

	public void setFuncionCoste(FitnessFunction expresion) {
		this.funcionCoste = expresion;
	}

	/**
	 * @return the funcionCoste
	 */
	public FitnessFunction getFuncionCoste() {
		return funcionCoste;
	}

	/**
	 * Calcula el coste para la cada individuo de la poblaci�n
	 * @throws Exception 
	 */
	public void calcularCostesPoblacion() throws Exception {
//		log.debug("***********calcula costes de la poblacion " + this.hashCode());
		for (Chromosome individuo : this.getCromosomas()) {
			individuo.calculateFitness(this.funcionCoste);
		}
//		log.debug("***********Fin del calculo costes de la poblacion " + this);
	}

	/**
	 * @return el mejor individuo de la poblaci�n
	 */
	public Chromosome obtenerMejor() {
		mejorCromosoma = Collections
				.max(cromosomas, new ComparadorMejorCoste());

		return mejorCromosoma;
	}

	/**
	 * @return el peor individuo de la poblacion
	 */
	public Chromosome obtenerPeor() {
		Chromosome peorCromosoma = Collections.min(cromosomas,
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
		List<Chromosome> listaCromosomas = new ArrayList<Chromosome>();
		for (Chromosome c:origen.getCromosomas()) {
			Chromosome nuevo = new Chromosome(c);
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
		for(Chromosome c: this.cromosomas) {
			sumaCostes+=c.getFitness();
		}
		return sumaCostes/this.tamanio;
	}

	public double calcularDesviacionTipica() {
		double media = this.calcularMediaCoste();
		double cuadradosAcumulados = 0;
		for(Chromosome c: this.cromosomas) {
			double diferencia = c.getFitness() - media;
			cuadradosAcumulados += (diferencia*diferencia);
		}
		return Math.sqrt(cuadradosAcumulados/this.tamanio);
	}

	
	/**
	 * Sustituye en la poblacion un cromosoma por otro
	 * @param antiguo
	 * @param nuevo
	 */
	public void sustituirCromosoma(Chromosome antiguo,
			Chromosome nuevo) {
		Collections.replaceAll(this.getCromosomas(), antiguo, new Chromosome(nuevo));
		mejorCromosoma = null;
	}


	


}
