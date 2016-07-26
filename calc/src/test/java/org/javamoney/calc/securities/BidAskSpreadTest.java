package org.javamoney.calc.securities;


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class BidAskSpreadTest {

    private static final Money ASK_PRICE = Money.of(37.8, "GBP");
    private static final Money BID_PRICE = Money.of(37.75, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(Money.of(0.05, "GBP"), BidAskSpread.calculate(ASK_PRICE, BID_PRICE));
    }
}