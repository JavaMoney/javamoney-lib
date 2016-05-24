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

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by atsticks on 15.05.16.
 * @see http://www.financeformulas.net/Future-Value-of-Annuity-Due.html#calcHeader
 */
public class FutureValueOfAnnuityDueTest {

    @Test
    public void getRate() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
               Rate.of(0.03),1
        );
        assertEquals(val.getRate(), Rate.of(0.03));
    }

    @Test
    public void getPeriods() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.03),3654
        );
        assertEquals(val.getPeriods(), 3654);
    }

    @Test
    public void of_Period1() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.05), 1
        );
        assertNotNull(val);
    }
    @Test
    public void of_Period0() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.08),0
        );
        assertNotNull(val);
    }

    @Test
    public void calculate_Periods0() throws Exception {
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.05), 0
        );
        assertEquals(Money.of(0,"CHF"), m.with(val));
        val = FutureValueOfAnnuityDue.of(
                Rate.of(-0.05), 0
        );
        assertEquals(Money.of(0,"CHF"), m.with(val));
    }


    @Test
    public void calculate_Periods1() throws Exception {
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.05), 1
        );
        assertEquals(Money.of(10.50,"CHF"), m.with(val));
        val = FutureValueOfAnnuityDue.of(
                Rate.of(-0.05), 1
        );
        assertEquals(Money.of(9.5,"CHF"), m.with(val));
    }

    @Test
    public void calculate_PeriodsN() throws Exception {
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.05), 10
        );
        assertEquals(Money.of(132.06787162326262,"CHF").getNumber().numberValue(BigDecimal.class)
                .doubleValue(), m.with(val).getNumber().numberValue(BigDecimal.class)
                .doubleValue(), 0.00000000000001d);
        val = FutureValueOfAnnuityDue.of(
                Rate.of(-0.05), 10
        );
        assertEquals(Money.of(76.23998154470802,"CHF").getNumber().numberValue(BigDecimal.class).doubleValue(),
                m.with(val).getNumber().numberValue(BigDecimal.class).doubleValue(), 0.000000000000001d);
    }

    @Test
    public void apply() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.08), 10
        );
        Money m = Money.of(10, "CHF");
        assertEquals(val.apply(m), m.with(val));
    }

    @Test
    public void toStringTest() throws Exception {
        FutureValueOfAnnuityDue val = FutureValueOfAnnuityDue.of(
                Rate.of(0.05), 10
        );
        assertEquals("FutureValueOfAnnuityDue{rate=Rate[0.05], periods=10}", val.toString());
    }

}