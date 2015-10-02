/**
 * 
 */
package com.fjgarcia.optimizadorga.algoritmo.selectores;


import com.fjgarcia.optimizadorga.elementos.Cromosoma;
import com.fjgarcia.optimizadorga.elementos.Poblacion;
/** 
 * Implementaci�n de un operador de selecci�n basado en el m�todo de
 *         la ruleta
 * @author Francisco Javier Garc�a Paredero
 */
public class SelectorRuleta implements Selector {
/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uned.optimizadorga.algoritmo.selectores.Selector#evaluar(com.uned
	 * .optimizadorga.elementos.Poblacion)
	 */
	@Override
	public Poblacion seleccionar(Poblacion poblacionInicial) {
		// Aplica el operador de selecci�n mediante el m�todo de la ruleta
		Poblacion poblacionSeleccionados = Poblacion.copiarPoblacionVacia(poblacionInicial);
		
		double offset = this.calcularOffset(poblacionInicial);
		// 1.- Calcula la suma total de los valores de la funcion de coste para
		// todos los cromosomas de la poblaci�n
		double sumaCoste = 0;
		for (Cromosoma c : poblacionInicial.getCromosomas()) {
			sumaCoste += c.getCoste() + offset;
		}

		// 2. Calcula la probabilidad de selecci�n para cada cromosoma
		// 3. Calcula la probabilidad acumulada
		double[] probabilidadesAcumuladas = new double[poblacionInicial
				.getTamanio()];
		double sumaProbabilidades = 0;
		int i = 0;
		for (Cromosoma c : poblacionInicial.getCromosomas()) {
			double probabilidadElemento = (c.getCoste()+offset) / sumaCoste;
			sumaProbabilidades += probabilidadElemento;
			probabilidadesAcumuladas[i] = sumaProbabilidades;
			i++;
		}		

		// 4. Se gira la ruleta pop_size veces
		for (int j = 0; j < poblacionInicial.getTamanio(); j++) {
			giroRuleta(poblacionInicial, poblacionSeleccionados,
					probabilidadesAcumuladas);
		}
		return poblacionSeleccionados;
	}

	/**
	 * Si la poblacion tiene valores de coste menores que 0, obtiene un offset para normalizarla
	 * @param poblacionInicial
	 * @return
	 */
	private double calcularOffset(Poblacion poblacionInicial) {
		double offset = 0;
		double minimo = poblacionInicial.obtenerPeor().getCoste();
		if (minimo<0) {
			offset = (-1 * minimo);
		}
		return offset;
	}

	/**
	 * Realiza un giro de la ruleta y a�ade el elemento de poblacion
	 * seleccionado a poblacionSeleccionados
	 * 
	 * @param poblacion
	 * @param poblacionSeleccionados
	 * @param probabilidadesAcumuladas
	 */
	private void giroRuleta(Poblacion poblacion,
			Poblacion poblacionSeleccionados, double[] probabilidadesAcumuladas) {
		// 4a. Se genera un numero aleatorio
		double numAleatorio = Math.random();
		// 4b. Selecciona el cromosoma en base al numero aleatorio
		if (numAleatorio < probabilidadesAcumuladas[0]) {
			poblacionSeleccionados.getCromosomas().add(new Cromosoma(
					poblacion.getCromosomas().get(0)));
		} else {
			int k = 1;
			try {
				boolean parar = false;
				while (!parar && k < probabilidadesAcumuladas.length-1) {
					double probab = probabilidadesAcumuladas[k];
					if (numAleatorio <= probab) {
						parar = true;
					} else {
						k++;
					}
				}
				poblacionSeleccionados.getCromosomas().add(new Cromosoma(
						poblacion.getCromosomas().get(k)));
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw e;
			}
			
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.uned.optimizadorga.algoritmo.selectores.Selector#getTipoSelector()
	 */
	@Override
	public String getTipoSelector() {
		return RULETA;
	}

}
