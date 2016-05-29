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
 * <img src="http://www.financeformulas.net/Formula%20Images/Annuity%20Due%20-%20FV%201.gif" />
 * <p>
 * The future value of annuity due formula is used to calculate the ending value of a series of
 * payments or cash flows where the first payment is received immediately. The first cash flow
 * received immediately is what distinguishes an annuity due from an ordinary annuity. An annuity
 * due is sometimes referred to as an immediate annuity.
 * <p>
 * The future value of annuity due formula calculates the value at a future date. The use of the
 * future value of annuity due formula in real situations is different than that of the present
 * value for an annuity due. For example, suppose that an individual or company wants to buy an
 * annuity from someone and the first payment is received today. To calculate the price to pay for
 * this particular situation would require use of the present value of annuity due formula. However,
 * if an individual is wanting to calculate what their balance would be after saving for 5 years in
 * an interest bearing account and they choose to put the first cash flow into the account today,
 * the future value of annuity due would be used.
 *
 * @author Anatole Tresch
 * @see http://www.financeformulas.net/Future-Value-of-Annuity-Due.html
 */
public final class FutureValueOfAnnuityDue implements MonetaryOperator {

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
    private FutureValueOfAnnuityDue(Rate rate, int periods) {
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
    public static FutureValueOfAnnuityDue of(Rate rate, int periods) {
        return new FutureValueOfAnnuityDue(rate, periods);
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
        BigDecimal base = CalculationContext.one().add(rate.get());
        BigDecimal counter = base.pow(periods, CalculationContext.mathContext()).subtract(BigDecimal.ONE);
        return amount.multiply(
                counter.divide(rate.get(), CalculationContext.mathContext()).multiply(base));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "FutureValueOfAnnuityDue{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
