package org.javamoney.calc.securities;

import static junit.framework.Assert.assertEquals;

import org.javamoney.calc.common.Rate;
import org.javamoney.moneta.Money;
import org.junit.Test;

/**
 * @author Manuela Grindei
 */
public class PreferredStockTest {

    private static final Money DIVIDEND = Money.of(20, "GBP");
    private static final Rate DISCOUNT_RATE = Rate.of(0.05);

    @Test
    public void testCalculate() {
        assertEquals(Money.of(400, "GBP"), PreferredStock.calculate(DIVIDEND, DISCOUNT_RATE));
	}
	
	@Test
	public void testApply() {
		assertEquals(Money.of(400, "GBP"), DIVIDEND.with(PreferredStock.of(DISCOUNT_RATE)));
    }
}