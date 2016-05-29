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
import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Compound%20Interest%201.gif" />
 * <p>
 * The compound interest formula calculates the amount of interest earned on an account or
 * investment where the amount earned is reinvested. By reinvesting the amount earned, an investment
 * will earn money based on the effect of compounding. Compounding is the concept that any amount
 * earned on an investment can be reinvested to of additional earnings that would not be
 * realized based on the original principal, or original balance, alone. The interest on the
 * original balance alone would be called simple interest. The additional earnings plus simple
 * interest would equal the total amount earned from compound interest.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @see http://www.financeformulas.net/Compound_Interest.html
 */
public final class CompoundInterest implements MonetaryOperator {

    /**
     * the target rate, not null.
     */
    private final Rate rate;
    /**
     * the periods, >= 0.
     */
    private final int periods;

    /**
     * The number of compoundings per time period.
     */
    private final int timesCompounded;

    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private CompoundInterest(Rate rate, int periods, int timesCompounded) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        if (timesCompounded < 0) {
            throw new IllegalArgumentException("TimesCompounded < 0");
        }
        this.periods = periods;
        this.timesCompounded = timesCompounded;
    }

    public int getPeriods() {
        return periods;
    }

    public Rate getRate() {
        return rate;
    }

    public int getTimesCompounded(){
        return timesCompounded;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the operator, never null.
     */
    public static CompoundInterest of(Rate rate, int periods, int timesCompounded) {
        return new CompoundInterest(rate, periods, timesCompounded);
    }

    /**
     * Access a MonetaryOperator for calculation, assuming one time compounding per time period.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the operator, never null.
     */
    public static CompoundInterest of(Rate rate, int periods) {
        return new CompoundInterest(rate, periods, 1);
    }

    /**
     * Performs the calculation, assuming timesCompounded/period = 1.
     *
     * @param amount  the base amount, not null.
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods) {
        return calculate(amount, rate, periods, 1);
    }


        /**
         * Performs the calculation.
         *
         * @param amount  the base amount, not null.
         * @param rate    the target rate, not null.
         * @param periods the periods, >= 0.
         * @return the resulting amount, never null.
         */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods, int timesCompounded) {
        final BigDecimal ONE = CalculationContext.one();
        BigDecimal part2 = rate.get().divide(BigDecimal.valueOf(timesCompounded));
        BigDecimal base = ONE.add(part2);
        BigDecimal multiplicator = base.pow(periods * timesCompounded);
        return amount.multiply(multiplicator).subtract(amount);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "CompoundInterest{" +
                "rate=" + rate +
                ", periods=" + periods +
                ", timesCompounded=" + timesCompounded +
                '}';
    }
}
