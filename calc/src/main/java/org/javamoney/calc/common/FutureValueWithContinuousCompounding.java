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

import javax.money.MonetaryAmount;

import com.ibm.icu.math.BigDecimal;

import java.util.Objects;

/**
 * <p>
 * <img
 * src="http://www.financeformulas.net/Formula%20Images/FV%20-%20Continuous%20Compounding%201.gif"
 * />
 * </p>
 * <p>
 * The future value with continuous compounding formula is used in calculating the later value of a
 * current sum of money. Use of the future value with continuous compounding formula requires
 * understanding of 3 general financial concepts, which are time value of money, future value as it
 * applies to the time value of money, and continuous compounding.
 * </p>
 * <h3>Time Value of Money, Future Value, and Continuous Compounding</h3>
 * <p>
 * <ul>
 * <li>Time Value of Money - The future value with continuous compounding formula relies on the
 * underlying concept of time value of money. Time value of money is the notion that a current sum
 * of money(or unit of account) is worth more today than the same amount at a future date.
 * <li>Future Value - Future value expands upon the idea of time value of money in that it
 * quantifies the amount required at a later date. For example, suppose that an individual has a
 * choice between receiving $1000 today or $1050 one year from today. Is the additional $50 worth
 * waiting one year for? Can the individual invest elsewhere and make a higher return? Could the
 * individual use the $1000 now for a higher "utility of enjoyment" than the $50 warrants? The
 * future value with continuous compounding formula calculates the later value when there is
 * continuous compounding.
 * <li>Continuous Compounding - Continuous compounding is compounding that is in constant motion as
 * opposed to incremental steps. Continuous compounding is considered to have an infinite amount of
 * compounding periods for a certain period of time because there is no incremental steps as found
 * in monthly or annual compounding.
 * </ul>
 * Particularly the last 2 of these concepts lends to the actual formula for future value with
 * continuous compounding.
 * </p>
 *
 * @author Anatole Tresch
 */
public final class FutureValueWithContinuousCompounding extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private FutureValueWithContinuousCompounding(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The discount rate and periods, not null.
     * @return the operator, never null.
     */
    public static FutureValueWithContinuousCompounding of(RateAndPeriods rateAndPeriods) {
        return new FutureValueWithContinuousCompounding(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rateAndPeriods    The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(amount, "Amount required");
        Rate rate = Objects.requireNonNull(rateAndPeriods.getRate(), "Rate required");
        int periods = rateAndPeriods.getPeriods();
        MonetaryAmount pv = PresentValue.calculate(amount, rateAndPeriods);
        BigDecimal fact = new BigDecimal(String.valueOf(Math.pow(Math.E, rate.get().doubleValue() * periods)));
        return pv.multiply(fact);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "FutureValueWithContinuousCompounding{" +
                "rateAndPeriods=" + rateAndPeriods +
                '}';
    }
}
