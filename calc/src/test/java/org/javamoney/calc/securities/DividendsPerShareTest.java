package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class DividendsPerShareTest {

    private static final Money DIVIDENDS = Money.of(500, "GBP");
    private static final int NUMBER_OF_SHARES = 20;

    @Test
    public void testCalculate() {
        assertEquals(Money.of(25, "GBP"), DividendsPerShare.calculate(DIVIDENDS, NUMBER_OF_SHARES));
    }
}