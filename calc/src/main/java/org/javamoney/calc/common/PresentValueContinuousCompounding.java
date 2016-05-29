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
import java.math.MathContext;
import java.util.Objects;

/**
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/PV%20-%20Cont%20Compounding%201.gif" />
 * </p>
 * <p>
 * The present value with continuous compounding formula is used to calculate the current value of a
 * future amount that has earned at a continuously compounded rate. There are 3 concepts to consider
 * in the present value with continuous compounding formula: time value of money, present value, and
 * continuous compounding.
 * </p>
 * <h3>Time Value of Money, Present Value, and Continuous Compounding</h3>
 * <p>
 * <ul>
 * <li>Time Value of Money - The present value with continuous compounding formula relies on the
 * concept of time value of money. Time value of money is the idea that a specific amount today is
 * worth more than the same amount at a future date. For example, if one were to be offered $1,000
 * today or $1,000 in 5 years, the presumption is that today would be preferable.
 * <li>Present Value - The basic premise of present value is the time value of money. To expand upon
 * the prior example, if one were to be offered $1,000 today or $1,250 in 5 years, the answer would
 * not be as obvious as the prior example where both amounts were equal. This is where present value
 * comes in. The offeree would need a way to determine today's value of the future amount of $1,250
 * to compare the two options.
 * <li>Continuous Compounding - Continuous Compounding is essentially compounding that is constant.
 * Ordinary compounding will have a compound basis such as monthly, quarterly, semi-annually, and so
 * forth. However, continuous compounding is nonstop, effectively having an infinite amount of
 * compounding for a given time.
 * </ul>
 * The present value with continuous compounding formula uses the last 2 of these concepts for its
 * actual calculations. The cash flow is discounted by the continuously compounded rate factor.
 * </p>
 * 
 * @author Anatole Tresch
 */
public final class PresentValueContinuousCompounding implements MonetaryOperator {

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
    private PresentValueContinuousCompounding(Rate rate, int periods) {
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
    public static PresentValueContinuousCompounding of(Rate rate, int periods) {
        return new PresentValueContinuousCompounding(rate, periods);
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
        BigDecimal fact = CalculationContext.one().divide(
                new BigDecimal(Math.pow(Math.E, rate
                .get().doubleValue() * periods), CalculationContext.mathContext()),
                CalculationContext.mathContext());
        return amount.multiply(fact);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "PresentValueContinuousCompounding{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
