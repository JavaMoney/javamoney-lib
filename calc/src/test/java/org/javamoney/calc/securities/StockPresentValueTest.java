/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: @manuela-grindei
 */
package org.javamoney.calc.securities;

import static junit.framework.Assert.assertEquals;

import org.javamoney.calc.common.Rate;
import org.javamoney.moneta.Money;
import org.junit.Test;

/**
 * @author Manuela Grindei
 */
public class StockPresentValueTest {

    private static final Money ESTIMATED_DIVIDENDS = Money.of(34, "GBP");
    private static final Rate REQUIRED_RATE_OF_RETURN = Rate.of(0.05);
    private static final Rate GROWTH_RATE = Rate.of(0.03);

    @Test
    public void testCalculateForConstantGrowth() {
        assertEquals(Money.of(1700, "GBP"), StockPresentValue.calculateForConstantGrowth(ESTIMATED_DIVIDENDS, REQUIRED_RATE_OF_RETURN, GROWTH_RATE));
    }

    @Test
    public void testCalculateForZeroGrowth() {
        assertEquals(Money.of(680, "GBP"), StockPresentValue.calculateForZeroGrowth(ESTIMATED_DIVIDENDS, REQUIRED_RATE_OF_RETURN));
	}
	
	@Test
	public void testApply() {
		assertEquals(Money.of(1700, "GBP"),
		    ESTIMATED_DIVIDENDS.with(StockPresentValue.of(REQUIRED_RATE_OF_RETURN, GROWTH_RATE)));
    }
}