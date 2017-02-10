package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class GeometricMeanReturnTest {

    private static final List<Rate> RATES_OF_RETURN = Arrays.asList(Rate.of(0.2), Rate.of(0.06), Rate.of(0.01));

    @Test
    public void testCalculate() {
        assertEquals(0.0871, GeometricMeanReturn.calculate(RATES_OF_RETURN), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullRatesThrowsException() {
        GeometricMeanReturn.calculate(Arrays.asList(Rate.of(0.1), Rate.of(0.1), null, Rate.of(0.5)));
    }
}