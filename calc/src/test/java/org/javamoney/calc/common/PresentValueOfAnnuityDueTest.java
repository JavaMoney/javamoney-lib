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

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by atsticks on 15.05.16.
 *
 * @link http ://www.financeformulas.net/Present_Value_of_Annuity_Due.html#calcHeader
 */
public class PresentValueOfAnnuityDueTest {

    /**
     * Gets rate.
     *
     * @throws Exception the exception
     */
    @Test
    public void getRate() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.03,1)
        );
        assertEquals(val.getRate(), Rate.of(0.03));
    }

    /**
     * Gets periods.
     *
     * @throws Exception the exception
     */
    @Test
    public void getPeriods() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.03,3654)
        );
        assertEquals(val.getPeriods(), 3654);
    }

    /**
     * Of period 1.
     *
     * @throws Exception the exception
     */
    @Test
    public void of_Period1() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.05, 1)
        );
        assertNotNull(val);
    }

    /**
     * Of period 0.
     *
     * @throws Exception the exception
     */
    @Test
    public void of_Period0() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.08,0)
        );
        assertNotNull(val);
    }

    /**
     * Calculate periods 0.
     *
     * @throws Exception the exception
     */
    @Test
    public void calculate_Periods0() throws Exception {
        Money m = Money.of(100, "CHF");
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.05, 0)
        );
        assertEquals(Money.of(0,"CHF"), m.with(val));
        val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(-0.05, 0)
        );
        assertEquals(Money.of(0,"CHF"), m.with(val));
    }


    /**
     * Calculate periods 1.
     *
     * @throws Exception the exception
     */
    @Test
    public void calculate_Periods1() throws Exception {
        Money m = Money.of(100, "CHF");
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.05, 1)
        );
        assertEquals(Money.of(100,"CHF"), m.with(val));
        val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(-0.05, 1)
        );
        assertEquals(Money.of(100,"CHF"), m.with(val));
    }

    /**
     * Calculate periods n.
     *
     * @throws Exception the exception
     */
    @Test
    public void calculate_PeriodsN() throws Exception {
        Money m = Money.of(100, "CHF");
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.05, 10)
        );
        assertEquals(Money.of(810.7821675644053,"CHF").getNumber().numberValue(BigDecimal.class)
                .doubleValue(), m.with(val).getNumber().numberValue(BigDecimal.class)
                .doubleValue(), 0.00000000000001d);
        val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(-0.05, 10)
        );
        assertEquals(Money.of(1273.3468832186768,"CHF").getNumber().numberValue(BigDecimal.class).doubleValue(),
                m.with(val).getNumber().numberValue(BigDecimal.class).doubleValue(), 0.000000000000001d);
    }

    /**
     * Apply.
     *
     * @throws Exception the exception
     */
    @Test
    public void apply() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.08, 10)
        );
        Money m = Money.of(100, "CHF");
        assertEquals(val.apply(m), m.with(val));
    }

    /**
     * To string test.
     *
     * @throws Exception the exception
     */
    @Test
    public void toStringTest() throws Exception {
        PresentValueOfAnnuityDue val = PresentValueOfAnnuityDue.of(
                RateAndPeriods.of(0.05, 10)
        );
        assertEquals("PresentValueOfAnnuityDue{\n" +
                " RateAndPeriods{\n" +
                "  rate=Rate[0.05]\n" +
                "  periods=10}}", val.toString());
    }

}