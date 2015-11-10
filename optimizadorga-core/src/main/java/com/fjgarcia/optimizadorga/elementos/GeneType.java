package com.fjgarcia.optimizadorga.elementos;
/**
 * Represents the genes coding
 * @author Francisco Javier Garcia Paredero
 *
 */
public class GeneType {
	// the name of this kind of gene
	private String name;	
	// a minimum value
	private double minimum;
	// a maximum value
	private double maximum;
	// A precision
	private int precission;
	

	/**
	 * @param name
	 * @param minimum
	 * @param maximum
	 * @param precission
	 */
	public GeneType(String name, double minimum, double maximum, int precission) {
		super();
		this.name = name;
		this.minimum = minimum;
		this.maximum = maximum;
		this.precission = precission;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the minimum
	 */
	public double getMinimum() {
		return this.minimum;
	}


	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}


	/**
	 * @return the maximum
	 */
	public double getMaximum() {
		return this.maximum;
	}


	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}


	/**
	 * @return the precission
	 */
	public int getPrecission() {
		return this.precission;
	}


	/**
	 * @param precission the precission to set
	 */
	public void setPrecission(int precission) {
		this.precission = precission;
	}
	
	
}
