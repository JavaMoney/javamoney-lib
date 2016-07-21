package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class BookValuePerShareTest {

    private static final Money EQUITY = Money.of(100, "GBP");
    private static final int NUMBER_OF_COMMON_SHARES = 10;

    @Test
    public void testCalculate() {
        assertEquals(Money.of(10, "GBP"), BookValuePerShare.calculate(EQUITY, NUMBER_OF_COMMON_SHARES));
    }
}