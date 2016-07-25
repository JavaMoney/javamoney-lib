package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class DividendYieldTest {

    private static final Money DIVIDENDS = Money.of(1.12, "GBP");
    private static final Money INITIAL_PRICE = Money.of(28, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(0.04, DividendYield.calculate(DIVIDENDS, INITIAL_PRICE).doubleValue());
    }
}