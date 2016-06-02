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

import javax.money.Monetary;
import javax.money.MonetaryException;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 29.05.16.
 */
public class BalloonLoanPaymentTest {

    @Test
    public void of_notNull() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),1, Money.of(5, "CHF")
        );
        assertNotNull(ci);
    }

    @Test
    public void of_correctRate() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.0234),1, Money.of(5, "CHF")
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.0234));
        ci = BalloonLoanPayment.of(
                Rate.of(0.05),1, Money.of(5, "CHF")
        );
        assertNotNull(ci.getRate());
        assertEquals(ci.getRate(),  Rate.of(0.05));
    }

    @Test
    public void of_correctPeriods() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),1, Money.of(5, "CHF")
        );
        assertEquals(ci.getPeriods(),  1);
        ci = BalloonLoanPayment.of(
                Rate.of(0.05),234, Money.of(5, "CHF")
        );
        assertEquals(ci.getPeriods(),  234);
    }

    @Test
    public void of_correctBalloonAmount() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),1, Money.of(5, "CHF")
        );
        assertEquals(ci.getBalloonAmount(),  Money.of(5, "CHF"));
        ci = BalloonLoanPayment.of(
                Rate.of(0.05),234, Money.of(11, "CHF")
        );
        assertEquals(ci.getBalloonAmount(),  Money.of(11, "CHF"));
    }

    @Test(expected=MonetaryException.class)
    public void calculate_zeroPeriods() throws Exception {
        BalloonLoanPayment.of(
                Rate.of(0.05),0, Money.of(5, "CHF")
        );
    }

    @Test
    public void calculate_onePeriods() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),1, Money.of(5, "CHF")
        );
        assertEquals(Money.of(100,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(100,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(-5,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(-6.05,"CHF"));
    }

    @Test
    public void calculate_twoPeriods() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),2, Money.of(5, "CHF")
        );
        assertEquals(Money.of(100,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(51.34,"CHF"));
        assertEquals(Money.of(0,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(-2.44,"CHF"));
        assertEquals(Money.of(-1,"CHF").with(ci).with(Monetary.getDefaultRounding()),Money.of(-2.98,"CHF"));
    }

    @Test
    public void apply() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),2, Money.of(5, "CHF")
        );
        assertEquals(ci.apply(Money.of(1,"CHF")).with(Monetary.getDefaultRounding()),Money.of(-1.9,"CHF"));
    }

    @Test
    public void test_toString() throws Exception {
        BalloonLoanPayment ci = BalloonLoanPayment.of(
                Rate.of(0.05),100, Money.of(5, "CHF")
        );
        assertEquals("BalloonLoanPayment{rate=Rate[0.05], periods=100, balloonAmount=CHF 5}",ci.toString());
    }
}