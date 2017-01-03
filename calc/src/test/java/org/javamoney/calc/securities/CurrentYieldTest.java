package org.javamoney.calc.securities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * @author Manuela Grindei
 */
public class CurrentYieldTest {

    private static final Money ANNUAL_COUPONS = Money.of(100, "GBP");
    private static final Money CURRENT_BOND_PRICE = Money.of(900, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(0.1111, CurrentYield.calculate(ANNUAL_COUPONS, CURRENT_BOND_PRICE).doubleValue(), 0.0001);
    }
}