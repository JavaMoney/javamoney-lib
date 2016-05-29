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

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import com.ibm.icu.math.BigDecimal;
import org.javamoney.calc.CalculationContext;

import java.math.MathContext;
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
public final class FutureValueWithContinuousCompounding implements MonetaryOperator {

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
    private FutureValueWithContinuousCompounding(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate The discount rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static FutureValueWithContinuousCompounding of(Rate rate, int periods) {
        return new FutureValueWithContinuousCompounding(rate, periods);
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
        MonetaryAmount pv = PresentValue.calculate(amount, rate, periods);
        BigDecimal fact = new BigDecimal(String.valueOf(Math.pow(Math.E, rate.get().doubleValue() * periods)));
        return pv.multiply(fact);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "FutureValueWithContinuousCompounding{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
