package org.javamoney.calc.securities;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class GeometricMeanReturnTest {

    private static final List<BigDecimal> RATES_OF_RETURN = Arrays.asList(BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.06), BigDecimal.valueOf(0.01));
    private static final int NUMBER_OF_PERIODS = 3;

    @Test
    public void testCalculate() {
        assertEquals(0.0871, GeometricMeanReturn.calculate(RATES_OF_RETURN, NUMBER_OF_PERIODS), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullRatesThrowsException() {
        GeometricMeanReturn.calculate(Arrays.asList(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1), null, BigDecimal.valueOf(0.5)), NUMBER_OF_PERIODS);
    }
}