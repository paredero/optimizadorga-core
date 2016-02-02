package com.uned.optimizadorga.algorithm.cache;

import java.util.HashMap;
import java.util.Map;

import com.uned.optimizadorga.model.Chromosome;

/**
 * Cache for storing all the chromosome objects in the system
 * 
 * When population is too big there is an explosion of objects.
 * Eg: A population of 1000 chromosomes with 100 generations and 10 eras means
 * 		1000000 objects just to store the chromosomes
 * This cache tries to reduce this number to the minimum expression
 * @author fpb
 *
 */
public class ChromosomeCache {
	private Map<Chromosome, Chromosome> allItems;
	
	public Chromosome getItem(Chromosome item) {
		if (!allItems.containsKey(item)) {
			allItems.put(item, item);			
		}
		return allItems.get(item);	
	}

	public ChromosomeCache() {
		super();
		this.allItems = new HashMap<Chromosome, Chromosome>();
	}
	
	
}
