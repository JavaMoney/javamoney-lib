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
import javax.money.MonetaryOperator;

/**
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/FV%20of%20Annuity%20-%20Continuous%20Compounding%201.gif"
 * />
 * <p>
 * The future value (FV) of an annuity with continuous compounding formula is used to calculate the
 * ending balance on a series of periodic payments that are compounded continuously. Understanding
 * the future value of annuity with continuous compounding formula requires the understanding of two
 * specific financial and mathematical concepts, which are future value of an annuity and continuous
 * compounding.
 * <h3>Future Value of Annuity, Continuous Compounding, and Geometric Series</h3>
 * <p>
 * Future Value of Annuity - The future value of an annuity is the sum of a series of periodic
 * payments and typically involves compounding of interest as the balance increases. The formula for
 * future value of annuity alone generally solves the question
 * "How much will I have saved at X dollars per month after Y months." Continuous Compounding -
 * Continuous compounding is compounding that is constant. One way some try to visualize the concept
 * of continuous compounding is that is fluid, constantly compounding moment by moment, as opposed
 * to daily, monthly, quarterly, or annually. The question a few sentences above regarding 'How much
 * will I have saved' must also take into consideration how often interest is compounded in the
 * interest bearing account. The formula for continuous compounding is
 * </p>
 * <h3>Continuous Compounding Formula</h3>
 * <p>
 * The future value of annuity with continuous compounding formula applies both of these concepts
 * for one saving in an account that has continuous compounding.
 * </p>
 *
 * @author Anatole Tresch
 * @see http://www.financeformulas.net/Future_Value_of_Annuity.html
 */
public final class FutureValueOfAnnuityWithContCompounding implements MonetaryOperator {

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
     * @param periods the periods.
     */
    private FutureValueOfAnnuityWithContCompounding(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        this.periods = periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate The discount rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static FutureValueOfAnnuityWithContCompounding of(Rate rate, int periods) {
        return new FutureValueOfAnnuityWithContCompounding(rate, periods);
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
        // FVofA/CC = CF * [ (e.pow(r*n) - 1) / ((e.pow(r) - 1)) ]
        double num = Math.pow(Math.E, rate.get().doubleValue() * periods) - 1.0;
        double denum = Math.pow(Math.E, rate.get().doubleValue()) - 1.0;
        BigDecimal factor = new BigDecimal(num, CalculationContext.mathContext())
                .divide(new BigDecimal(denum, CalculationContext.mathContext()));
        return amount.multiply(factor);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

}
