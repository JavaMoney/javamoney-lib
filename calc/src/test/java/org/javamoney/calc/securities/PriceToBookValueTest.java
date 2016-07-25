package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class PriceToBookValueTest {

    private static final Money MARKET_PRICE_PER_SHARE = Money.of(120, "GBP");
    private static final Money BOOK_VALUE_PER_SHARE = Money.of(240, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.5), PriceToBookValue.calculate(MARKET_PRICE_PER_SHARE, BOOK_VALUE_PER_SHARE));
    }
}