package com.fjgarcia.optimizadorga.algoritmo;

import java.util.ArrayList;
import java.util.List;

import com.fjgarcia.optimizadorga.algoritmo.interfaces.EraObserver;
import com.fjgarcia.optimizadorga.algoritmo.interfaces.EraSubject;
import com.fjgarcia.optimizadorga.elementos.Configuracion;
import com.fjgarcia.optimizadorga.elementos.Chromosome;
import com.fjgarcia.optimizadorga.elementos.Poblacion;

/**
 * Clase que contiene la ejecucion de una era
 * Es observable por el algoritmo, al que informa de su proggreso
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Era implements EraSubject {
	private Configuracion configuracion;
	private Poblacion poblacionInicial;
	private List<EraObserver> observadores;
	// Aqu� conservo las sucesivas poblaciones resultado de la evolucion 
	// en la computacion
	private List<Poblacion> evolucionPoblaciones;
	private Chromosome mejorIndividuo;
	

	
	public Era() {
		super();
		observadores = new ArrayList<EraObserver>();
	}

	/**
	 * @param configuracion the configuracion to set
	 */
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Realiza la ejecucion de la era
	 * @throws Exception
	 */
	public void ejecutar() throws Exception {
		if (!Thread.currentThread().isInterrupted()) {
			this.inicializarPoblacion();
		}
		if (!Thread.currentThread().isInterrupted()) {
			this.ejecutarBucle();
		}
	}
	
	private void inicializarPoblacion() throws Exception {
		this.poblacionInicial = Poblacion.generarPoblacionInicializada(configuracion);
		this.evolucionPoblaciones = new ArrayList<Poblacion>(configuracion.getMaxGens());
		this.evolucionPoblaciones.add(poblacionInicial);
	}
	
	private void ejecutarBucle() throws Exception {
		int generacionActual = 0;
		while (!Thread.currentThread().isInterrupted() && generacionActual < configuracion.getMaxGens()) {
			Generacion generacion = new Generacion(poblacionInicial,
					configuracion);
			generacionActual++;
			generacion.ejecutar();
			// A�ado el resultado de la generacion que es una nueva poblacion
			this.evolucionPoblaciones.add(generacion.getNuevaPoblacion());
			// La poblacion inicial de la siguiente generacion sera la obtenida en la ultima iteracion
			this.poblacionInicial = generacion.getNuevaPoblacion();
			this.notifyGeneracion(generacion);
		}
	}

	/**
	 * @return  El mejor cromosoma obtenido en la computaci�n hasta el momento
	 */
	public Chromosome obtenerMejor() {
		mejorIndividuo = this.evolucionPoblaciones.get(
				this.evolucionPoblaciones.size() - 1).obtenerMejor();
		return mejorIndividuo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.interfaces.EraSubject#registerObserver(com.uned.optimizadorga.algoritmo.interfaces.EraObserver)
	 */
	@Override
	public void registerObserver(EraObserver observer) {
		observadores.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.interfaces.EraSubject#notifyGeneracion(com.uned.optimizadorga.algoritmo.Generacion)
	 */
	@Override
	public void notifyGeneracion(Generacion generacionProcesada) {
		for (EraObserver o:this.observadores) {
			o.updateGeneracion(generacionProcesada);
		}
	}

	/**
	 * Metodo para liberar memoria
	 */
	public void liberarRecursos() {
		this.poblacionInicial = null;
		this.evolucionPoblaciones = null;
	}

	/**
	 * @return Devuelve las poblaciones que se han ido generando durante la ejecucion de las generaciones de una era
	 */
	public List<Poblacion> getEvolucionPoblaciones() {
		return this.evolucionPoblaciones;
	}
}
