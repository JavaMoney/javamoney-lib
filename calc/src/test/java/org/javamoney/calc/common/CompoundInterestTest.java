/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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

import static org.junit.Assert.*;

/**
 * Tests for compouning, including with multiple compounding rounds.
 */
public class CompoundInterestTest {

    @Test
    public void of_notNull() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci);
    }

    @Test
    public void of_correctRate() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.0234),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.0234));
        ci = CompoundInterest.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.05));
    }

    @Test
    public void of_correctPeriods() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),1
        );
        assertEquals(ci.getPeriods(),  1);
        ci = CompoundInterest.of(
                Rate.of(0.05),234
        );
        assertEquals(ci.getPeriods(),  234);
    }

    @Test
    public void calculate_zeroPeriods() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),0
        );
        assertEquals(Money.of(10.03,"CHF").with(ci), Money.of(0,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci), Money.of(0,"CHF"));
        assertEquals(Money.of(-20.45,"CHF").with(ci), Money.of(0,"CHF"));
    }

    @Test
    public void calculate_onePeriods() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),1
        );
        assertEquals(Money.of(1,"CHF").with(ci),Money.of(0.05,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci),Money.of(0,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci),Money.of(-0.05,"CHF"));
    }

    @Test
    public void calculate_twoPeriods() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),2
        );
         assertEquals(Money.of(1,"CHF").with(ci),Money.of(0.1025,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci),Money.of(0,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci),Money.of(-0.1025,"CHF"));
    }

    @Test
    public void apply() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),2
        );
        assertEquals(ci.apply(Money.of(1,"CHF")),Money.of(0.1025,"CHF"));
    }

    @Test
    public void apply_With12compoundings() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.1), 10, 24
        );
        assertEquals(ci.apply(Money.of(1,"CHF")),Money.of(1.5937424601,"CHF"));
    }

    @Test
    public void test_toString() throws Exception {
        CompoundInterest ci = CompoundInterest.of(
                Rate.of(0.05),100
        );
        assertEquals("CompoundInterest{rate=Rate[0.05], periods=100, timesCompounded=1}",ci.toString());
    }
}