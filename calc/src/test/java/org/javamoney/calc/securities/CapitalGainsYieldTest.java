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


import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Capital gains yield test.
 *
 * @author Manuela Grindei
 */
public class CapitalGainsYieldTest {

    private static final Money INITIAL_STOCK_PRICE = Money.of(123, "GBP");
    private static final Money STOCK_PRICE_AFTER_FIRST_PERIOD = Money.of(223, "GBP");

    /**
     * Test calculate.
     */
    @Test
    public void testCalculate() {
        assertEquals(0.81301, CapitalGainsYield.calculate(INITIAL_STOCK_PRICE, STOCK_PRICE_AFTER_FIRST_PERIOD).doubleValue(), 0.00001);
    }
}