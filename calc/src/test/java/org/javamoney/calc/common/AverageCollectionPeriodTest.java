/*
 *  Copyright (c) 2012, 2013, Trivadis (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.common;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by atsticks on 27.05.16.
 * @see http://www.financeformulas.net/Average-Collection-Period.html#calcHeader
 */
public class AverageCollectionPeriodTest {

    @Test
    public void calculate_POSITIVE() throws Exception {
        assertEquals(99.23871669385536, AverageCollectionPeriod.calculate(BigDecimal.valueOf(3.678)).doubleValue(), 0.0000001d);
    }

    @Test
    public void calculate_NEGATIVE() throws Exception {
        assertEquals(-99.23871669385536, AverageCollectionPeriod.calculate(BigDecimal.valueOf(-3.678)).doubleValue(), 0.0000001d);
    }

    @Test
    public void calculate_Explicit() throws Exception {
        assertEquals(BigDecimal.valueOf(1042.006525285481), AverageCollectionPeriod.calculate(Money.of(3.678, "CHF"),
                BigDecimal.valueOf(10.5)));
    }

    @Test
    public void receivableTurnover() throws Exception {
        assertEquals(BigDecimal.valueOf(2.854812398042414), AverageCollectionPeriod.receivablesTurnover(
                Money.of(3.678, "CHF"), BigDecimal.valueOf(10.5)));
    }


}