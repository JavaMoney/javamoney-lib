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

import static junit.framework.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class EstimatedEarningsTest {

    private static final Money FORECASTED_SALES = Money.of(500, "GBP");
    private static final Money FORECASTED_EXPENSES = Money.of(300, "GBP");
    private static final Money PROJECTED_SALES = Money.of(100, "GBP");
    private static final BigDecimal PROJECTED_NET_PROFIT_MARGIN = BigDecimal.valueOf(0.02);

    @Test
    public void testCalculate() {
        assertEquals(Money.of(200, "GBP"), EstimatedEarnings.calculate(FORECASTED_SALES, FORECASTED_EXPENSES));
    }

    @Test
    public void testCalculateWithProfitMarginFormula() {
        assertEquals(Money.of(2, "GBP"), EstimatedEarnings.calculate(PROJECTED_SALES, PROJECTED_NET_PROFIT_MARGIN));
    }
}