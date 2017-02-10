package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class PriceToEarningsRatioTest {

    private static final Money PRICE_PER_SHARE = Money.of(100, "GBP");
    private static final Money EARNINGS_PER_SHARE = Money.of(400, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.25), PriceToEarningsRatio.calculate(PRICE_PER_SHARE, EARNINGS_PER_SHARE));
    }
}