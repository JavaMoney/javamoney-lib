/*
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.common;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.money.*;

import org.javamoney.moneta.Money;
import org.junit.Test;

/**
 * Tests for the {@link org.javamoney.calc.common.PresentValue} formula calculator.
 */
public class PresentValueTest{

    /**
     * Method: of(Rate rate, int periods)
     */
    @Test
    public void testOfAndApply() throws Exception {
        Money money = Money.of(100, "CHF");
        MonetaryOperator rounding = MonetaryRoundings.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(Money.of(BigDecimal.valueOf(95.24), "CHF"), money.with(PresentValue.of(Rate.of(0.05), 1)).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(90.70), "CHF"), money.with(PresentValue.of(Rate.of(0.05), 2)).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(47.51), "CHF"), money.with(PresentValue.of(Rate.of(0.07), 11)).with(rounding));
    }

    /**
     * Method: calculate(MonetaryAmount amount, Rate rate, int periods)
     */
    @Test
    public void testCalculate() throws Exception {
        Money money = Money.of(100, "CHF");
        MonetaryOperator rounding = MonetaryRoundings.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(Money.of(BigDecimal.valueOf(95.24), "CHF"), PresentValue.calculate(money, Rate.of(0.05), 1).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(90.70), "CHF"), PresentValue.calculate(money, Rate.of(0.05), 2).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(47.51), "CHF"), PresentValue.calculate(money, Rate.of(0.07), 11).with(rounding));
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        assertEquals("PresentValue{rate=Rate[0.05], periods=1}", PresentValue.of(Rate.of(0.05), 1).toString());
        assertEquals("PresentValue{rate=Rate[0.05], periods=2}", PresentValue.of(Rate.of(0.05), 2).toString());
        assertEquals("PresentValue{rate=Rate[0.07], periods=11}", PresentValue.of(Rate.of(0.07), 11).toString());
    }

}
