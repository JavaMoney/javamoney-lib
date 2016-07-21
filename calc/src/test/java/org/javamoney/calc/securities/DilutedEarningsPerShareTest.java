package org.javamoney.calc.securities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class DilutedEarningsPerShareTest {

    private static final Money NET_INCOME = Money.of(1155, "GBP");
    private static final BigDecimal AVERAGE_SHARES = BigDecimal.valueOf(100.6);
    private static final BigDecimal OTHER_CONVERTIBLE_INSTRUMENTS = BigDecimal.valueOf(4.4);

    @Test
    public void testCalculate() {
        assertEquals(Money.of(11, "GBP"), DilutedEarningsPerShare.calculate(NET_INCOME, AVERAGE_SHARES, OTHER_CONVERTIBLE_INSTRUMENTS));
    }
}