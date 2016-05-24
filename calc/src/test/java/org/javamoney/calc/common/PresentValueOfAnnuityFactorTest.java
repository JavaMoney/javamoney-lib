package org.javamoney.calc.common;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 15.05.16.
 * TODO value seem not to be reasonable, check!!!
 */
public class PresentValueOfAnnuityFactorTest {

    @Test
    public void calculate_periods0() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 0));
    }

    @Test
    public void calculate_periods1() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 1));
    }

    @Test
    public void calculate_periods10() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 10));
    }
}