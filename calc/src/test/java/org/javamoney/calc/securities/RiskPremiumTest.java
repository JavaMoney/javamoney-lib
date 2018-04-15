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

import static junit.framework.Assert.assertEquals;

/**
 * The type Risk premium test.
 *
 * @author Manuela Grindei
 */
public class RiskPremiumTest {

    private static final Rate ASSET_RETURN = Rate.of(0.06);
    private static final Rate RISK_FREE_RETURN = Rate.of(0.02);
    private static final BigDecimal BETA = BigDecimal.valueOf(0.5);
    private static final Rate MARKET_RETURN = Rate.of(0.07);

    /**
     * Test calculate.
     */
    @Test
    public void testCalculate() {
        assertEquals(BigDecimal.valueOf(0.04), RiskPremium.calculate(ASSET_RETURN, RISK_FREE_RETURN));
    }

    /**
     * Test calculate with capm.
     */
    @Test
    public void testCalculateWithCAPM() {
        assertEquals(BigDecimal.valueOf(0.025), RiskPremium.calculateWithCAPM(BETA, MARKET_RETURN, RISK_FREE_RETURN));
    }
}