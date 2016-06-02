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
package org.javamoney.calc.banking;

import org.javamoney.calc.common.Rate;
import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 29.05.16.
 */
public class AnnualPercentageYieldTest {

    @Test
    public void of_notNull() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci);
    }

    @Test
    public void of_correctRate() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.0234),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.0234));
        ci = AnnualPercentageYield.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.05));
    }

    @Test
    public void of_correctPeriods() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),1
        );
        assertEquals(ci.getPeriods(),  1);
        ci = AnnualPercentageYield.of(
                Rate.of(0.05),234
        );
        assertEquals(ci.getPeriods(),  234);
    }

    @Test
    public void calculate_zeroPeriods() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),0
        );
    }

    @Test
    public void calculate_onePeriods() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),1
        );
        assertEquals(0.05d, AnnualPercentageYield.calculate(
                Rate.of(0.05),1).get().doubleValue(), 0.0d);
        assertEquals(Money.of(1,"CHF").with(ci),Money.of(0.05,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci),Money.of(0,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci),Money.of(-0.05,"CHF"));
    }

    @Test
    public void calculate_twoPeriods() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),2
        );
        assertEquals(Money.of(1,"CHF").with(ci),Money.of(0.050625,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci),Money.of(0.0,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci),Money.of(-0.050625,"CHF"));
    }

    @Test
    public void apply() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),2
        );
        assertEquals(ci.apply(Money.of(1,"CHF")),Money.of(0.050625,"CHF"));
    }

    @Test
    public void test_toString() throws Exception {
        AnnualPercentageYield ci = AnnualPercentageYield.of(
                Rate.of(0.05),100
        );
        assertEquals("AnnualPercentageYield{rate=Rate[0.05], periods=100}",ci.toString());
    }
}