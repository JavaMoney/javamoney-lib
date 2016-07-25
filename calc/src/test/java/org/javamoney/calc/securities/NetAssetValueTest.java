package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class NetAssetValueTest {

    private static final Money ASSETS = Money.of(1_000_000, "GBP");
    private static final Money FUND_LIABILITIES = Money.of(100_000, "GBP");
    private static final int OUTSTANDING_SHARES = 100_000;

    @Test
    public void testCalculate() {
        assertEquals(Money.of(9, "GBP"), NetAssetValue.calculate(ASSETS, FUND_LIABILITIES, OUTSTANDING_SHARES));
    }
}