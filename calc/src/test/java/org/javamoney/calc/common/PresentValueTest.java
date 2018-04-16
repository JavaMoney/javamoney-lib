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
 * Contributors: @atsticks, @keilw
 */
package org.javamoney.calc.common;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.money.*;

import org.junit.Test;

/**
 * Tests for the {@link org.javamoney.calc.common.PresentValue} formula calculator.
 *
 * @link http ://www.financeformulas.net/Present_Value.html#calcHeader
 */
public class PresentValueTest{

    /**
     * Method: of(Rate rate, int periods)
     *
     * @throws Exception the exception
     */
    @Test
    public void testOfAndApply() throws Exception {
        MonetaryAmountFactory fact = Monetary.getDefaultAmountFactory();
        MonetaryAmount money = fact.setCurrency("CHF").setNumber(100).create();
        MonetaryOperator rounding = Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(fact.setNumber(95.24).create(), money.with(PresentValue.of(RateAndPeriods.of(0.05, 1))).with(rounding));
        assertEquals(fact.setNumber(90.70).create(), money.with(PresentValue.of(RateAndPeriods.of(0.05, 2))).with(rounding));
        assertEquals(fact.setNumber(47.51).create(), money.with(PresentValue.of(RateAndPeriods.of(0.07, 11))).with(rounding));

        assertEquals(fact.setNumber(100.00).create(), money.with(PresentValue.of(RateAndPeriods.of(0.05, 0))).with(rounding));
        assertEquals(fact.setNumber(100.00).create(), money.with(PresentValue.of(RateAndPeriods.of(-0.05, 0))).with(rounding));

        assertEquals(fact.setNumber(105.26).create(), money.with(PresentValue.of(RateAndPeriods.of(-0.05, 1))).with(rounding));
        assertEquals(fact.setNumber(110.80).create(), money.with(PresentValue.of(RateAndPeriods.of(-0.05, 2))).with(rounding));
        assertEquals(fact.setNumber(222.17).create(), money.with(PresentValue.of(RateAndPeriods.of(-0.07, 11))).with(rounding));
    }

    /**
     * Method: calculate(MonetaryAmount amount, Rate rate, int periods)
     *
     * @throws Exception the exception
     */
    @Test
    public void testCalculate() throws Exception {
        MonetaryAmountFactory fact = Monetary.getDefaultAmountFactory();
        MonetaryAmount money = fact.setNumber(100).setCurrency("CHF").create();
        MonetaryOperator rounding = Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_EVEN)
                .build());
        assertEquals(fact.setNumber(95.24).create(), PresentValue.calculate(money, RateAndPeriods.of(0.05, 1)).with(rounding));
        assertEquals(fact.setNumber(90.70).create(), PresentValue.calculate(money, RateAndPeriods.of(0.05, 2)).with(rounding));
        assertEquals(fact.setNumber(47.51).create(), PresentValue.calculate(money, RateAndPeriods.of(0.07, 11)).with(rounding));
    }

    /**
     * Method: toString()
     *
     * @throws Exception the exception
     */
    @Test
    public void testToString() throws Exception {
        assertEquals("PresentValue{\n" +
                " RateAndPeriods{\n" +
                "  rate=Rate[0.05]\n" +
                "  periods=1}}", PresentValue.of(RateAndPeriods.of(0.05, 1)).toString());
        assertEquals("PresentValue{\n" +
                " RateAndPeriods{\n" +
                "  rate=Rate[0.05]\n" +
                "  periods=2}}", PresentValue.of(RateAndPeriods.of(0.05, 2)).toString());
        assertEquals("PresentValue{\n" +
                " RateAndPeriods{\n" +
                "  rate=Rate[0.07]\n" +
                "  periods=11}}", PresentValue.of(RateAndPeriods.of(0.07, 11)).toString());
    }

}
