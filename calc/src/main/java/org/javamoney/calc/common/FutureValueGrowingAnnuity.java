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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.MonetaryOperator;

/**
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Growing%20Annuity%20-%20FV%201.gif"/>
 * <br>
 * <p>
 * The formula for the future value of a growing annuity is used to calculate the future amount of a
 * series of cash flows, or payments, that grow at a proportionate rate. A growing annuity may
 * sometimes be referred to as an increasing annuity.
 * <p>
 * <h3>Example of FV of Growing Annuity</h3>
 * <p>
 * An example of the future value of a growing annuity formula would be an individual who is paid
 * biweekly and decides to save one of her extra paychecks per year. One of her net paychecks
 * amounts to $2,000 for the first year and she expects to receive a 5% raise on her net pay every
 * year. For this example, we will use 5% on her net pay and not involve taxes and other adjustments
 * in order to hold all other things constant. In an account that has a yield of 3% per year, she
 * would like to calculate her savings balance after 5 years.
 * <p>
 * The growth rate in this example would be the 5% increase per year, the initial cash flow or
 * payment would be $2,000, the number of periods would be 5 years, and rate per period would be 3%.
 * Using these variables in the future value of growing annuity formula would show
 * <p>
 * <i>Example</i>
 * <p>
 * After solving this equation, the amount after the 5th cash flow would be $11,700.75.
 *
 * @author Anatole Tresch
 * @see http://www.financeformulas.net/Future-Value-of-Growing-Annuity.html
 */
public final class FutureValueGrowingAnnuity implements MonetaryOperator {
    /**
     * The discount rate, not null.
     */
    private final Rate discountRate;
    /**
     * The growth rate, not null.
     */
    private final Rate growthRate;
    /**
     * the target periods, >= 0.
     */
    private final int periods;

    /**
     * Constructor.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     */
    private FutureValueGrowingAnnuity(Rate discountRate, Rate growthRate, int periods) {
        this.discountRate = Objects.requireNonNull(discountRate);
        this.growthRate = Objects.requireNonNull(growthRate);
        if(discountRate.equals(growthRate)){
            throw new MonetaryException("Discount rate and growth rate cannot be the same.");
        }
        if (periods < 0) {
            throw new MonetaryException("Periods < 0");
        }
        this.periods = periods;
    }

    public Rate getDiscountRate() {
        return discountRate;
    }

    public Rate getGrowthRate() {
        return growthRate;
    }

    public int getPeriods() {
        return periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static FutureValueGrowingAnnuity of(Rate discountRate, Rate growthRate, int periods) {
        return new FutureValueGrowingAnnuity(discountRate, growthRate, periods);
    }

    /**
     * Performs the calculation.
     *
     * @param firstPayment the first payment
     * @param discountRate The rate perperiod, not null. If the rate is less than 0, the result will be zero.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount firstPayment, Rate discountRate, Rate growthRate,
                                           int periods) {
        if(discountRate.equals(growthRate)){
            throw new MonetaryException("Discount rate and growth rate cannot be the same.");
        }
        if(discountRate.get().signum()<0){
            return firstPayment.getFactory().setNumber(0.0d).create();
        }
        final BigDecimal ONE = CalculationContext.one();
        BigDecimal num = ONE.add(discountRate.get()).pow(periods)
                .subtract(ONE.add(growthRate.get()).pow(periods));
        BigDecimal denum = discountRate.get().subtract(growthRate.get());
        return firstPayment.multiply(num.divide(denum, CalculationContext.mathContext()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount firstPayment) {
        return calculate(firstPayment, discountRate, growthRate, periods);
    }

    @Override
    public String toString() {
        return "FutureValueGrowingAnnuity{" +
                "discountRate=" + discountRate +
                ", growthRate=" + growthRate +
                ", periods=" + periods +
                '}';
    }
}
