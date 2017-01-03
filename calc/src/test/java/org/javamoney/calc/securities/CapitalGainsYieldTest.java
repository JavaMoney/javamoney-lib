package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class CapitalGainsYieldTest {

    private static final Money INITIAL_STOCK_PRICE = Money.of(123, "GBP");
    private static final Money STOCK_PRICE_AFTER_FIRST_PERIOD = Money.of(223, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(0.81301, CapitalGainsYield.calculate(INITIAL_STOCK_PRICE, STOCK_PRICE_AFTER_FIRST_PERIOD).doubleValue(), 0.00001);
    }
}