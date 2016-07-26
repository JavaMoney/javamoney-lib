package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class RiskPremiumTest {

    private static final Rate ASSET_RETURN = Rate.of(0.06);
    private static final Rate RISK_FREE_RETURN = Rate.of(0.02);
    private static final BigDecimal BETA = BigDecimal.valueOf(0.5);
    private static final Rate MARKET_RETURN = Rate.of(0.07);

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.04), RiskPremium.calculate(ASSET_RETURN, RISK_FREE_RETURN));
    }

    @Test
    public void testCalculateWithCAPM() {
        assertEquals(BigDecimal.valueOf(0.025), RiskPremium.calculateWithCAPM(BETA, MARKET_RETURN, RISK_FREE_RETURN));
    }
}