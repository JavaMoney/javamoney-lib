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


import org.javamoney.calc.common.Rate;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * The type Capital asset pricing model formula test.
 *
 * @author Manuela Grindei
 */
public class CapitalAssetPricingModelFormulaTest {

    private static final Rate RISKFREE_RATE = Rate.of(0.1);
    private static final BigDecimal BETA = BigDecimal.valueOf(2);
    private static final Rate MARKET_RETURN = Rate.of(0.2);
    private static final BigDecimal EPSILON = BigDecimal.valueOf(0.001);

    /**
     * Test calculate with regression.
     */
    @Test
    public void testCalculateWithRegression() {
        assertEquals(Rate.of(0.301), CapitalAssetPricingModelFormula.calculate(RISKFREE_RATE, BETA, MARKET_RETURN, EPSILON));
    }

    /**
     * Test calculate.
     */
    @Test
    public void testCalculate() {
        assertEquals(Rate.of(0.3), CapitalAssetPricingModelFormula.calculate(RISKFREE_RATE, BETA, MARKET_RETURN));
    }
}