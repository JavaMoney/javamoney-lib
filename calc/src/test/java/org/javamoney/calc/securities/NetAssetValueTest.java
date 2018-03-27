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
 * @author Manuela Grindei
 */
public class NetAssetValueTest {

    private static final Money ASSETS = Money.of(1_000_000, "GBP");
    private static final Money FUND_LIABILITIES = Money.of(100_000, "GBP");
    private static final int OUTSTANDING_SHARES = 100_000;

    @Test
    public void testCalculate() {
        assertEquals(Money.of(9, "GBP"), NetAssetValue.calculate(ASSETS, FUND_LIABILITIES, OUTSTANDING_SHARES));
    }
}