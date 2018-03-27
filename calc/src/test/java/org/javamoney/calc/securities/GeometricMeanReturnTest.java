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
public class GeometricMeanReturnTest {

    private static final List<Rate> RATES_OF_RETURN = Arrays.asList(Rate.of(0.2), Rate.of(0.06), Rate.of(0.01));

    @Test
    public void testCalculate() {
        assertEquals(0.0871, GeometricMeanReturn.calculate(RATES_OF_RETURN), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullRatesThrowsException() {
        GeometricMeanReturn.calculate(Arrays.asList(Rate.of(0.1), Rate.of(0.1), null, Rate.of(0.5)));
    }
}