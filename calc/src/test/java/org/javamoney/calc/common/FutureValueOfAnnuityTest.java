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

import javax.money.MonetaryException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Tests for FV of annuities. Compare values with
 * {@code http://www.financeformulas.net/Future_Value_of_Annuity.html#calcHeader}.
 */
public class FutureValueOfAnnuityTest {

    /**
     * Gets rate.
     *
     * @throws Exception the exception
     */
    @Test
    public void getRate() throws Exception {
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
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
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
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
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
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
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
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
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(0.05, 0)
        );
        assertEquals(Money.of(0,"CHF"), m.with(val));
        val = FutureValueOfAnnuity.of(
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
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(0.05, 1)
        );
        assertEquals(Money.of(10,"CHF"), m.with(val));
        val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(-0.05, 1)
        );
        assertEquals(Money.of(10,"CHF"), m.with(val));
    }

    /**
     * Calculate periods n.
     *
     * @throws Exception the exception
     */
    @Test
    public void calculate_PeriodsN() throws Exception {
        Money m = Money.of(10, "CHF");
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(0.05, 10)
        );
        assertEquals(Money.of(125.7789253554883,"CHF").getNumber().numberValue(BigDecimal.class)
                .doubleValue(), m.with(val).getNumber().numberValue(BigDecimal.class)
                .doubleValue(), 0.00000000000001d);
        val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(-0.05, 10)
        );
        assertEquals(Money.of(80.25261215232422,"CHF").getNumber().numberValue(BigDecimal.class).doubleValue(),
                m.with(val).getNumber().numberValue(BigDecimal.class).doubleValue(), 0.000000000000001d);
    }

    /**
     * Apply.
     *
     * @throws Exception the exception
     */
    @Test
    public void apply() throws Exception {
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(0.08, 10)
        );
        Money m = Money.of(10, "CHF");
        assertEquals(val.apply(m), m.with(val));
    }

    /**
     * To string test.
     *
     * @throws Exception the exception
     */
    @Test
    public void toStringTest() throws Exception {
        FutureValueOfAnnuity val = FutureValueOfAnnuity.of(
                RateAndPeriods.of(0.05, 10)
        );
        assertEquals("FutureValueOfAnnuity{\n" +
                " RateAndPeriods{\n" +
                "  rate=Rate[0.05]\n" +
                "  periods=10}}", val.toString());
    }

}