package com.fjgarcia.optimizadorga.test;

import org.junit.Assert;
import org.junit.Test;

import com.uned.optimizadorga.algorithm.selectors.RouletteSelector;
import com.uned.optimizadorga.algorithm.selectors.Selector;
import com.uned.optimizadorga.algorithm.selectors.SelectorFactory;
import com.uned.optimizadorga.algorithm.selectors.SelectorType;
import com.uned.optimizadorga.algorithm.selectors.TournamentSelector;

public class SelectorFactoryTest {

	@Test
	public void testGetTournamentSelector() {
		Selector tornamentSelector = SelectorFactory.getInstance(SelectorType.TOURNAMENT);
		Assert.assertTrue("Sould get a Tournament instance", tornamentSelector instanceof TournamentSelector);
	}

	@Test
	public void testGetRouletteSelector() {
		Selector rouletteSelector = SelectorFactory.getInstance(SelectorType.ROULETTE);
		Assert.assertTrue("Sould get a Roulette instance", rouletteSelector instanceof RouletteSelector);
	}
	
	@Test
	public void testGetDefaultSelector() {
		Selector rouletteSelector = SelectorFactory.getInstance(SelectorType.ROULETTE);
		Assert.assertTrue("Sould get a Roulette instance", rouletteSelector instanceof RouletteSelector);
	}
}
