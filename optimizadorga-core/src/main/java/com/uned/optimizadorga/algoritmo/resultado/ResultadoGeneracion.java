package com.uned.optimizadorga.algoritmo.resultado;

import java.util.List;

import com.uned.optimizadorga.algorithm.Generation;
import com.uned.optimizadorga.algorithm.comparators.ComparadorMejorCoste;
import com.uned.optimizadorga.elementos.Configuration;
import com.uned.optimizadorga.elementos.Chromosome;
import com.uned.optimizadorga.elementos.Gen;

/**
 * Metodo para calcular los resultados de una generacion
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class ResultadoGeneracion extends Resultado {
	
	private double desviacionTipica;
	private double porcentajeMejora;
	private Chromosome mejorCromosomaGeneracion;
	private double mediaCostePoblacion;

	/**
	 * Metodo de factoria estatica
	 * @param generacion
	 * @param startTime
	 * @param resultadosEras
	 * @param resultadosGeneraciones
	 * @param configuracion
	 * @return el resultado del calculo de una generaci�n
	 */
	public static ResultadoGeneracion crearResultadoGeneracion(Generation generacion,
			long startTime, List<ResultadoEra> resultadosEras, List<ResultadoGeneracion> resultadosGeneraciones,
			Configuration configuracion) {
		ResultadoGeneracion r = new ResultadoGeneracion();
		long timeParcial = System.currentTimeMillis();
		r.setTiempoEjecucion((timeParcial - startTime)/1000);
		r.setCambioGeneracion(true);
		r.setCambioEra(false);
		r.setGeneracionActual(resultadosGeneraciones.size()+1); //+1 para contarlas como 1, 2, 3...
		r.setEraActual(resultadosEras.size()+1);
		Chromosome mejorCromosomaGeneracion = generacion.getEvolvedPopulation().obtainFittest();
		Chromosome antiguoMejorCromosoma = generacion.getPoblacionInicial().obtainFittest();
		
		if (new ComparadorMejorCoste().compare(mejorCromosomaGeneracion,
				antiguoMejorCromosoma) >= 0) {
			r.setMejorCromosomaGeneracion(mejorCromosomaGeneracion);
		} else {
			r.setMejorCromosomaGeneracion(antiguoMejorCromosoma);
		}
		
		r.setMediaCostePoblacion(generacion.getEvolvedPopulation().calcularMediaCoste());
		r.setDesviacionTipica(generacion.getEvolvedPopulation().calcularDesviacionTipica());
		r.setPorcentajeMejora(((mejorCromosomaGeneracion.getCoste() - antiguoMejorCromosoma
				.getCoste()) / antiguoMejorCromosoma.getCoste()) * 100);
		r.setProgreso(calcularProgreso(r.getEraActual(), r.getGeneracionActual(), configuracion));
		return r;
	}


	
	public ResultadoGeneracion() {
		super();
	}



	protected static int calcularProgreso(int eraActual, int generacionActual,
			Configuration configuracion) {
		// log.debug("****************PROGRESO********************************");
		double progreso = 0;
		int numEra = eraActual-1;
		double numGeneracion = generacionActual;
		double totalGeneraciones = configuracion.getMaxGens();
		double totalEras = configuracion.getMaxEras();

		progreso = (((numEra) / totalEras) * 100) + ((numGeneracion / (totalEras * totalGeneraciones)) * 100);
		return (int) progreso;
	}	

	@Override
	public String printResultado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Generaci�n Actual: ").append(this.generacionActual).append("\n");
		sb.append("Mejor Cromosoma obtenido hasta el momento: ");	
		for (Gen g:this.getMejorCromosomaGeneracion().getGenes()) {
			sb.append("[").append(g.getTipoGen().getNombre()).append(",").append(g.getValor()).append("]");
		}
		sb.append("\nCoste: ").append(this.getMejorCromosomaGeneracion().getCoste()).append("\n");
		sb.append("Valor medio del coste: ").append(this.getMediaCostePoblacion()).append("\n");
		sb.append("Desviaci�n t�pica del coste: ").append(this.getDesviacionTipica()).append("\n");
		sb.append("Porcentaje de mejora: ").append(this.getPorcentajeMejora());
		return sb.toString();
	}

	/**
	 * @return the desviacionTipica
	 */
	public double getDesviacionTipica() {
		return this.desviacionTipica;
	}


	/**
	 * @param desviacionTipica the desviacionTipica to set
	 */
	public void setDesviacionTipica(double desviacionTipica) {
		this.desviacionTipica = desviacionTipica;
	}


	/**
	 * @return the porcentajeMejora
	 */
	public double getPorcentajeMejora() {
		return this.porcentajeMejora;
	}


	/**
	 * @param porcentajeMejora the porcentajeMejora to set
	 */
	public void setPorcentajeMejora(double porcentajeMejora) {
		this.porcentajeMejora = porcentajeMejora;
		
	}


	/**
	 * @return the mejorCromosomaGeneracion
	 */
	public Chromosome getMejorCromosomaGeneracion() {
		return this.mejorCromosomaGeneracion;
	}


	/**
	 * @param mejorCromosomaGeneracion the mejorCromosomaGeneracion to set
	 */
	public void setMejorCromosomaGeneracion(Chromosome mejorCromosomaGeneracion) {
		this.mejorCromosomaGeneracion = mejorCromosomaGeneracion;
	}
	
	
	public void setMediaCostePoblacion(double mediaCostePoblacion) {
		this.mediaCostePoblacion = mediaCostePoblacion;
	}
	
	

	/**
	 * @return the mediaCostePoblacion
	 */
	public double getMediaCostePoblacion() {
		return this.mediaCostePoblacion;
	}
	
}
