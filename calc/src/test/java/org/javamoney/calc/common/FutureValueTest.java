/*
 * Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil.
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
 */
package org.javamoney.calc.common;

import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryOperator;
import javax.money.Monetary;
import javax.money.RoundingQueryBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link org.javamoney.calc.common.FutureValue} formula.
 * @see http://www.financeformulas.net/Future_Value.html#calcHeader
 */
public class FutureValueTest {

    /**
     * Method: of(Rate ratePerPeriod, int periods).
     */
    @Test
    public void testOfAndApply() throws Exception {
        Money money = Money.of(100, "CHF");
        MonetaryOperator rounding = Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(Money.of(BigDecimal.valueOf(105.00), "CHF"), money.with(FutureValue.of(Rate.of(0.05), 1)).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(110.25), "CHF"), money.with(FutureValue.of(Rate.of(0.05), 2)).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(210.49), "CHF"), money.with(FutureValue.of(Rate.of(0.07), 11)).with(rounding));
    }

    /**
     * Method: calculate(MonetaryAmount amount, Rate rate, int periods)
     */
    @Test
    public void testCalculate() throws Exception {
        Money money = Money.of(100, "CHF");
        MonetaryOperator rounding = Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(Money.of(BigDecimal.valueOf(105.00), "CHF"), FutureValue.calculate(money, Rate.of(0.05), 1).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(110.25), "CHF"), FutureValue.calculate(money, Rate.of(0.05), 2).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(210.49), "CHF"), FutureValue.calculate(money, Rate.of(0.07), 11).with(rounding));
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        assertEquals("FutureValue{rate=Rate[0.05], periods=1}", FutureValue.of(Rate.of(0.05), 1).toString());
        assertEquals("FutureValue{rate=Rate[0.05], periods=2}", FutureValue.of(Rate.of(0.05), 2).toString());
        assertEquals("FutureValue{rate=Rate[0.07], periods=11}", FutureValue.of(Rate.of(0.07), 11).toString());
    }

    @Test
    public void testFormula() {
        Money money = Money.of(100, "CHF");
        MonetaryOperator rounding = Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(Money.of(BigDecimal.valueOf(105.00), "CHF"), FutureValue.calculate(money, Rate.of(0.05), 1).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(110.25), "CHF"), FutureValue.calculate(money, Rate.of(0.05), 2).with(rounding));
        assertEquals(Money.of(BigDecimal.valueOf(210.49), "CHF"), FutureValue.calculate(money, Rate.of(0.07), 11).with(rounding));
    }
}
