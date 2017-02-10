/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 */
package org.javamoney.calc.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for discount factor.
 */
public class DiscountFactorTest {

    @Test
	public void calculate_Negative() {
        assertEquals(1, DiscountFactor.calculate(Rate.of(-0.05),0).doubleValue(), 0.0d);
        assertEquals(1.0526315789473684, DiscountFactor.calculate(Rate.of(-0.05),1).doubleValue(), 0.0d);
        assertEquals(1.422382169222759, DiscountFactor.calculate(Rate.of(-0.05),10).doubleValue(), 0.0d);
    }

    @Test
	public void calculate_Zero() {
        assertEquals(1, DiscountFactor.calculate(Rate.of(0.00),0).intValueExact());
        assertEquals(1, DiscountFactor.calculate(Rate.of(0.00),1).intValueExact());
        assertEquals(1, DiscountFactor.calculate(Rate.of(0.00),10).intValueExact());
    }

    @Test
	public void calculate_Positive() {
        assertEquals(1.0, DiscountFactor.calculate(Rate.of(0.05),0).doubleValue(), 0.0d);
        assertEquals(0.95, DiscountFactor.calculate(Rate.of(0.05),1).doubleValue(), 0.0d);
        assertEquals(0.37110537322255859375, DiscountFactor.calculate(Rate.of(0.05),10).doubleValue(), 0.0d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void calculate_negativePeriods() {
		DiscountFactor.calculate(Rate.of(-0.05), -1);
    }
}