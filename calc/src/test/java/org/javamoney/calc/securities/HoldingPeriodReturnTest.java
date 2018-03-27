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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuela Grindei
 */
public class HoldingPeriodReturnTest {

    private static final List<Rate> RATES_OF_RETURN = Arrays.asList(Rate.of(0.1), Rate.of(0.05), Rate.of(-0.02));
    private static final Rate PERIODIC_RATE = Rate.of(0.2);
    private static final int NUMBER_OF_PERIODS = 3;

    @Test
    public void testCalculate() {
        assertEquals(0.1319, HoldingPeriodReturn.calculate(RATES_OF_RETURN).doubleValue(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullReturnsThrowsException() {
        HoldingPeriodReturn.calculate(Arrays.asList(Rate.of(0.1), Rate.of(0.1), null, Rate.of(0.5)));
    }

    @Test
    public void testCalculateForSameReturn() {
        assertEquals(0.728, HoldingPeriodReturn.calculateForSameReturn(PERIODIC_RATE, NUMBER_OF_PERIODS).doubleValue(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForSameReturnWithNullRateThrowsException() {
        HoldingPeriodReturn.calculateForSameReturn(null, NUMBER_OF_PERIODS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForSameReturnWithNegativeNumberOfPeriodsThrowsException() {
        HoldingPeriodReturn.calculateForSameReturn(PERIODIC_RATE, -1);
    }
}