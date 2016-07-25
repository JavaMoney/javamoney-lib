package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class DividendYieldTest {

    @Test
    public void testCalculate() {
        assertEquals(0.04, DividendYield.calculate(Money.of(1.12, "GBP"), Money.of(28, "GBP")).doubleValue());
    }
}