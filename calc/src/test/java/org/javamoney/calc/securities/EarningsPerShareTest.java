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

import static junit.framework.Assert.assertEquals;

/**
 * The type Earnings per share test.
 *
 * @author Manuela Grindei
 */
public class EarningsPerShareTest {

    private static final Money NET_INCOME = Money.of(82, "GBP");
    private static final double WEIGHTED_AVERAGE_OF_OUTSTANDING_SHARES = 20.5;

    /**
     * Test calculate.
     */
    @Test
    public void testCalculate() {
        assertEquals(Money.of(4, "GBP"), EarningsPerShare.calculate(NET_INCOME, WEIGHTED_AVERAGE_OF_OUTSTANDING_SHARES));
    }
}