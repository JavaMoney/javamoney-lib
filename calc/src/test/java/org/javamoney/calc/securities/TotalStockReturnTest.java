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
public class TotalStockReturnTest {

    private static final Money INITIAL_PRICE = Money.of(1000, "GBP");
    private static final Money ENDING_PRICE = Money.of(1020, "GBP");
    private static final Money DIVIDENDS = Money.of(20, "GBP");

    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.04), TotalStockReturn.calculate(INITIAL_PRICE, ENDING_PRICE, DIVIDENDS));
    }
}