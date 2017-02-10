package org.javamoney.calc.securities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class ZeroCouponBondYieldTest {

    private static final Money FACE_AMOUNT = Money.of(34, "GBP");
    private static final Money PRESENT_AMOUNT = Money.of(12, "GBP");
    private static final int NUMBER_OF_PERIODS = 4;

    @Test
    public void testCalculate() {
        assertEquals(0.2974, ZeroCouponBondYield.calculate(FACE_AMOUNT, PRESENT_AMOUNT, NUMBER_OF_PERIODS), 0.0001);
    }
}