package com.uned.optimizadorga.model;
/**
 * A flyweight implementation of the gene coding
 * @author Francisco Javier Garcia Paredero
 *
 */
public class GeneType {
	private String name;	
	private double min;
	private double max;
	private int precission;
	
	/**
	 * Used by Jackson serialization	
	 */
	public GeneType() {
		super();
	}

	/**
	 * @param name
	 * @param min
	 * @param max
	 * @param precission
	 */
	public GeneType(String name, double min, double max, int precission) {
		super();
		this.name = name;
		this.min = min;
		this.max = max;
		this.precission = precission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getPrecission() {
		return precission;
	}

	public void setPrecission(int precission) {
		this.precission = precission;
	}
}
