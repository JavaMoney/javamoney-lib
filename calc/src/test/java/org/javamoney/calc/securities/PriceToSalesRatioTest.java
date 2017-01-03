package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class PriceToSalesRatioTest {

    private static final Money SHARE_PRICE = Money.of(100, "GBP");
    private static final Money SALES_PER_SHARE = Money.of(500, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.2), PriceToSalesRatio.calculate(SHARE_PRICE, SALES_PER_SHARE));
    }
}