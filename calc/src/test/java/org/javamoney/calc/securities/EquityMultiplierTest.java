package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class EquityMultiplierTest {

    private static final Money TOTAL_ASSETS = Money.of(500, "GBP");
    private static final Money EQUITY = Money.of(1000, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(0.5, EquityMultiplier.calculate(TOTAL_ASSETS, EQUITY).doubleValue());
    }
}