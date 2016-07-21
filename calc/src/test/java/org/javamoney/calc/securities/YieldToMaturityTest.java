package org.javamoney.calc.securities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class YieldToMaturityTest {

    private static final Money COUPON_PAYMENT_AMOUNT = Money.of(100, "GBP");
    private static final Money FACE_AMOUNT = Money.of(1000, "GBP");
    private static final Money PRICE_AMOUNT = Money.of(920, "GBP");
    private static final int NUMBER_OF_YEARS_TO_MATURITY = 10;

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.1125), YieldToMaturity.calculate(COUPON_PAYMENT_AMOUNT, FACE_AMOUNT, PRICE_AMOUNT, NUMBER_OF_YEARS_TO_MATURITY));
    }
}