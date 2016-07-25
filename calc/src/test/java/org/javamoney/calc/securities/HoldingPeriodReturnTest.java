package org.javamoney.calc.securities;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class HoldingPeriodReturnTest {

    private static final List<BigDecimal> RATES_OF_RETURN = Arrays.asList(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.05), BigDecimal.valueOf(-0.02));
    private static final BigDecimal PERIODIC_RATE = BigDecimal.valueOf(0.2);
    private static final int NUMBER_OF_PERIODS = 3;

    @Test
    public void testCalculate() {
        assertEquals(0.1319, HoldingPeriodReturn.calculate(RATES_OF_RETURN).doubleValue(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullReturnsThrowsException() {
        HoldingPeriodReturn.calculate(Arrays.asList(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1), null, BigDecimal.valueOf(0.5)));
    }

    @Test
    public void testCalculateForSameReturn() {
        assertEquals(0.728, HoldingPeriodReturn.calculateForSameReturn(PERIODIC_RATE, NUMBER_OF_PERIODS).doubleValue(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForSameReturnWithNullRateThrowsException() {
        HoldingPeriodReturn.calculateForSameReturn(null, NUMBER_OF_PERIODS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForSameReturnWithNegativeNumberOfPeriodsThrowsException() {
        HoldingPeriodReturn.calculateForSameReturn(PERIODIC_RATE, -1);
    }
}