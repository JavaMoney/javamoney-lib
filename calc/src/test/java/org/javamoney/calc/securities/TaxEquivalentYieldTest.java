package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class TaxEquivalentYieldTest {

    private static final Rate TAX_FREE_YIELD = Rate.of(0.04);
    private static final Rate TAX_RATE = Rate.of(0.33);

    @Test
    public void testCalculate() {
        assertEquals(0.0597, TaxEquivalentYield.calculate(TAX_FREE_YIELD, TAX_RATE).doubleValue(), 0.0001);
    }
}