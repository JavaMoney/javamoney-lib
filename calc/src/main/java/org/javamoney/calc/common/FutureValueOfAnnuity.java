/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/FV%20of%20Annuity%204.gif" />
 * <p>
 * The future value of an annuity formula is used to calculate what the value at a future date would
 * be for a series of periodic payments. The future value of an annuity formula assumes that
 * <p>
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value of each individual
 * cash flow would need to be calculated to determine the future value of the annuity. If the first
 * cash flow, or payment, is made immediately, the {@link org.javamoney.calc.common.FutureValue} formula would be used.
 *
 * @author Anatole
 * @author Werner
 * @see http://www.financeformulas.net/Future_Value_of_Annuity.html
 */
public final class FutureValueOfAnnuity implements MonetaryOperator {
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
    private FutureValueOfAnnuity(Rate rate, int periods) {
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
     * @param rate the rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static FutureValueOfAnnuity of(Rate rate, int periods) {
        return new FutureValueOfAnnuity(rate, periods);
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
        // Am * (((1 + r).pow(n))-1/rate)
        final BigDecimal ONE = CalculationContext.one();
        return amount.multiply(ONE.add(rate.get()).pow(periods).subtract(ONE).divide(
                rate.get(),CalculationContext.mathContext()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "FutureValueOfAnnuity{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
