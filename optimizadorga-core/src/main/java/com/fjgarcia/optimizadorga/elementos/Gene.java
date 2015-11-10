/**
 * 
 */
package com.fjgarcia.optimizadorga.elementos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


/**
 * A gene, with its coding and value
 * @author Francisco Javier Garc�a Paredero
 *
 */
public class Gene {
	private double value;	
	private GeneType geneType;

	public Gene(GeneType geneType) {
		super();
		this.geneType = geneType;
	}
	
	public Gene(Gene g) {
		super();
		this.geneType = g.getGeneType();
		this.value = g.getValue();
	}


	/**
	 * Initializes the value with a random double value respecting the maximum,
	 * minimum and precission
	 */
	public void generateRandomValue() {
		double randomNumber = geneType.getMinimum()
				+ (Math.random() * (geneType.getMaximum() - geneType
						.getMinimum()));
		this.value = format(randomNumber);
	}
	
	/**
	 * Redondea un valor double a la precisi�n asignada por la codificacion
	 * @param value
	 * @return el valor formateado
	 */
	private double format(double value) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setGroupingUsed(Boolean.FALSE);
		df.setDecimalFormatSymbols(dfs);
		df.setMaximumFractionDigits(geneType.getPrecission());
		String valorStr = df.format(value);
		return Double.parseDouble(valorStr);
	}
	


	/**
	 * @return the value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the geneType
	 */
	public GeneType getGeneType() {
		return this.geneType;
	}

	/**
	 * @param geneType the geneType to set
	 */
	public void setGeneType(GeneType geneType) {
		this.geneType = geneType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + geneType.getName() + ", " + value + "]";
	}

}
