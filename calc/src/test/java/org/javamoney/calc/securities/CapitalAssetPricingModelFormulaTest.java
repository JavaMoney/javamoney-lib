package org.javamoney.calc.securities;


import org.javamoney.calc.common.Rate;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class CapitalAssetPricingModelFormulaTest {

    private static final Rate RISKFREE_RATE = Rate.of(0.1);
    private static final BigDecimal BETA = BigDecimal.valueOf(2);
    private static final Rate MARKET_RETURN = Rate.of(0.2);
    private static final BigDecimal EPSILON = BigDecimal.valueOf(0.001);

    @Test
    public void testCalculateWithRegression() {
        assertEquals(Rate.of(0.301), CapitalAssetPricingModelFormula.calculate(RISKFREE_RATE, BETA, MARKET_RETURN, EPSILON));
    }

    @Test
    public void testCalculate() {
        assertEquals(Rate.of(0.3), CapitalAssetPricingModelFormula.calculate(RISKFREE_RATE, BETA, MARKET_RETURN));
    }
}