package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class BidAskSpreadTest {

    @Test
    public void testCalculate() {
        assertEquals(Money.of(0.05, "GBP"), BidAskSpread.calculate(Money.of(37.8, "GBP"), Money.of(37.75, "GBP")));
    }
}