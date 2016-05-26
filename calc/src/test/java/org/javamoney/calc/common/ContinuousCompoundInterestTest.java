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
 * Tests for continous compouning.
 */
public class ContinuousCompoundInterestTest {

    @Test
    public void of_notNull() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci);
    }

    @Test
    public void of_correctRate() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.0234),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.0234));
        ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),1
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.05));
    }

    @Test
    public void of_correctPeriods() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),1
        );
        assertEquals(ci.getPeriods(),  1);
        ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),234
        );
        assertEquals(ci.getPeriods(),  234);
    }

    @Test
    public void calculate_zeroPeriods() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),0
        );
        assertEquals(Money.of(10.03,"CHF").with(ci), Money.of(0,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci), Money.of(0,"CHF"));
        assertEquals(Money.of(-20.45,"CHF").with(ci), Money.of(0,"CHF"));
    }

    @Test
    public void calculate_onePeriods() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),1
        );
        assertEquals(Money.of(1,"CHF").with(ci).getNumber().doubleValue(),
                0.0512d, 0.0001d);
        assertEquals(Money.of(0,"CHF").with(ci).getNumber().doubleValue(),
                0.0d, 0.000d);
        assertEquals(Money.of(-1,"CHF").with(ci).getNumber().doubleValue(),
                -0.0512d, 0.0001d);
    }

    @Test
    public void calculate_twoPeriods() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),2
        );
        assertEquals(Money.of(1,"CHF").with(ci).getNumber().doubleValue(),
                0.10517064178387361, 0.000000001d);
        assertEquals(Money.of(0,"CHF").with(ci).getNumber().doubleValue(),0d, 0.0d);
        assertEquals(Money.of(-1,"CHF").with(ci).getNumber().doubleValue(),
                -0.10517064178387361, 0.000000001d);
    }

    @Test
    public void apply() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.1),2
        );
        // Values are confirmed:
        assertEquals(ci.apply(Money.of(1000,"CHF"))
                .getNumber().doubleValue(),
                Money.of(221.40153676616498,"CHF").getNumber().doubleValue(),
                0.00d);
    }

    @Test
    public void test_toString() throws Exception {
        ContinuousCompoundInterest ci = ContinuousCompoundInterest.of(
                Rate.of(0.05),2
        );
        assertEquals("ContinuousCompoundInterest{rate=Rate[0.05], periods=2}", ci.toString());
    }
}