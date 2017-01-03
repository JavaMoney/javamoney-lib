package org.javamoney.calc.securities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class TotalStockReturnTest {

    private static final Money INITIAL_PRICE = Money.of(1000, "GBP");
    private static final Money ENDING_PRICE = Money.of(1020, "GBP");
    private static final Money DIVIDENDS = Money.of(20, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.04), TotalStockReturn.calculate(INITIAL_PRICE, ENDING_PRICE, DIVIDENDS));
    }
}