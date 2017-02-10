package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class EstimatedEarningsTest {

    private static final Money FORECASTED_SALES = Money.of(500, "GBP");
    private static final Money FORECASTED_EXPENSES = Money.of(300, "GBP");
    private static final Money PROJECTED_SALES = Money.of(100, "GBP");
    private static final BigDecimal PROJECTED_NET_PROFIT_MARGIN = BigDecimal.valueOf(0.02);

    @Test
    public void testCalculate() {
        assertEquals(Money.of(200, "GBP"), EstimatedEarnings.calculate(FORECASTED_SALES, FORECASTED_EXPENSES));
    }

    @Test
    public void testCalculateWithProfitMarginFormula() {
        assertEquals(Money.of(2, "GBP"), EstimatedEarnings.calculate(PROJECTED_SALES, PROJECTED_NET_PROFIT_MARGIN));
    }
}