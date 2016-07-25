package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class DividendPayoutRatioTest {

    private static final Money DIVIDENDS = Money.of(120, "GBP");
    private static final Money NET_INCOME = Money.of(150, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.8), DividendPayoutRatio.calculate(DIVIDENDS, NET_INCOME));
    }
}