/**
 * 
 */
package com.uned.optimizadorga.elementos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa un cromosoma
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Chromosome {
	private double coste;
	private List<Gen> genes;

	public static Chromosome generarCromosomaAleatorio(Map<String,TipoGen> parametros) {
		Chromosome cromosoma = new Chromosome();
		Iterator<String> itClaveParametros = parametros.keySet().iterator();
		while (itClaveParametros.hasNext()) {
			TipoGen tipoGen = parametros.get(itClaveParametros.next());
			Gen genResultado = new Gen(tipoGen);
			genResultado.generarValorAleatorio();
			cromosoma.getGenes().add(genResultado);
		}
		return cromosoma;
	}

	
	public Chromosome(Chromosome cromosomaOrigen) {
		super();
		this.coste = cromosomaOrigen.getCoste();
		genes = new ArrayList<Gen>();
		for (Gen g:cromosomaOrigen.getGenes()) {
			genes.add(new Gen(g));
		}
	}
	/**
	 * @return the genes
	 */
	public List<Gen> getGenes() {
		return genes;
	}

	/**
	 * @param genes
	 *            the genes to set
	 */
	public void setGenes(List<Gen> genes) {
		this.genes = genes;
	}

	/**
	 * @return the coste
	 */
	public double getCoste() {
		return coste;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\t" + this.hashCode() +" [coste=" + coste + ", genes=" + genes + "]";
	}

	public Chromosome() {
		super();
		this.genes = new ArrayList<Gen>();
	}

	public void calcularCoste(Funcion funcionCoste) throws Exception {
		this.coste = funcionCoste.evaluate(genes);
	}

}
