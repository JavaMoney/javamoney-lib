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

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * The type Price to sales ratio test.
 *
 * @author Manuela Grindei
 */
public class PriceToSalesRatioTest {

    private static final Money SHARE_PRICE = Money.of(100, "GBP");
    private static final Money SALES_PER_SHARE = Money.of(500, "GBP");

    /**
     * Test calculate.
     */
    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.2), PriceToSalesRatio.calculate(SHARE_PRICE, SALES_PER_SHARE));
    }
}