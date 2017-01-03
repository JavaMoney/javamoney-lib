package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class BondEquivalentYieldTest {

    private static final Money FACE_AMOUNT = Money.of(1000, "GBP");
    private static final Money PRICE_AMOUNT = Money.of(900, "GBP");
    private static final int NUMBER_OF_DAYS_TO_MATURITY = 100;

    @Test
    public void testCalculate() {
        assertEquals(0.40556, BondEquivalentYield.calculate(FACE_AMOUNT, PRICE_AMOUNT, NUMBER_OF_DAYS_TO_MATURITY).doubleValue(), 0.00001);
    }
}