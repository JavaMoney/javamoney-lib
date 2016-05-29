/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;

import java.math.BigDecimal;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * The formula for the present value of an annuity due,
 * sometimes referred to as an immediate annuity, is used to calculate a series of periodic
 * payments, or cash flows, that start immediately.
 * 
 * @see http://www.financeformulas.net/Present_Value_of_Annuity_Due.html
 * @author Anatole Tresch
 * TODO Check test values!!!
 */
public final class PresentValueOfAnnuityDue implements MonetaryOperator {

    /**
     * the target rate, not null.
     */
    private Rate rate;
    /**
     * the periods, >= 0.
     */
    private int periods;

    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private PresentValueOfAnnuityDue(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    public int getPeriods() {
        return periods;
    }

    public Rate getRate() {
        return rate;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate The rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static PresentValueOfAnnuityDue of(Rate rate, int periods) {
        return new PresentValueOfAnnuityDue(rate, periods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rate    The rate, not null.
     * @param periods the target periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rate, "Rate required");
        if(periods==0){
            return amount.getFactory().setNumber(0.0).create();
        }else if(periods==1){
            return amount;
        }
        return PresentValueOfAnnuity.calculate(amount, rate, periods)
                .multiply(rate.get().add(CalculationContext.one()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "PresentValueOfAnnuityDue{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
