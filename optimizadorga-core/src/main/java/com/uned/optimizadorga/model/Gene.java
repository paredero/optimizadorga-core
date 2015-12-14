/**
 * 
 */
package com.uned.optimizadorga.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * It represent a gene, with a value and a codification
 * 
 * @author Francisco Javier Garcia Paredero
 *
 */
public class Gene {
	private double value;
	private GeneType geneType;

	/**
	 * Constructs an empty gene, with only a codification
	 * 
	 * @param geneType
	 */
	public Gene(GeneType geneType) {
		super();
		this.geneType = geneType;

	}

	/**
	 * Constructs a gene that is a copy of another gene
	 * 
	 * @param originalGene
	 */
	public Gene(Gene originalGene) {
		super();
		this.geneType = originalGene.getGeneType();
		this.value = originalGene.getValue();
	}

	/**
	 * Initializes the value with a random real number between max and min with
	 * the configurated precission
	 */
	public void generateRandomValue() {
		double randomValue = geneType.getMin() + (Math.random() * (geneType.getMax() - geneType.getMin()));
		this.value = format(randomValue);
	}

	/**
	 * Formats the double value to the coded precission
	 * 
	 * @param value
	 * @return The formatted value
	 */
	private double format(double value) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setGroupingUsed(Boolean.FALSE);
		df.setDecimalFormatSymbols(dfs);
		df.setMaximumFractionDigits(geneType.getPrecission());
		String strValue = df.format(value);
		return Double.parseDouble(strValue);
	}

	

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public GeneType getGeneType() {
		return geneType;
	}

	public void setGeneType(GeneType geneType) {
		this.geneType = geneType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + geneType.getName() + ", " + value + "]";
	}

}
