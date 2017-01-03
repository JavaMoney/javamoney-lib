package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class EarningsPerShareTest {

    private static final Money NET_INCOME = Money.of(82, "GBP");
    private static final double WEIGHTED_AVERAGE_OF_OUTSTANDING_SHARES = 20.5;

    @Test
    public void testCalculate() {
        assertEquals(Money.of(4, "GBP"), EarningsPerShare.calculate(NET_INCOME, WEIGHTED_AVERAGE_OF_OUTSTANDING_SHARES));
    }
}