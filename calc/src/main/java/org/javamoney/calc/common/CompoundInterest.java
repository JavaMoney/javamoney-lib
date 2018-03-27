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

import static org.javamoney.calc.CalculationContext.mathContext;
import static org.javamoney.calc.CalculationContext.one;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

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
 * @link http://www.financeformulas.net/Compound_Interest.html
 */
public final class CompoundInterest extends AbstractRateAndPeriodBasedOperator {

    /**
     * The number of compoundings per time period.
     */
    private final int timesCompounded;

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate, not null.
     * @param timesCompounded the times to be compunded, >= 0.
     */
    private CompoundInterest(RateAndPeriods rateAndPeriods, int timesCompounded) {
        super(rateAndPeriods);
        if (timesCompounded < 0) {
            throw new IllegalArgumentException("TimesCompounded < 0");
        }
        this.timesCompounded = timesCompounded;
    }


    public int getTimesCompounded(){
        return timesCompounded;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the operator, never null.
     */
    public static CompoundInterest of(RateAndPeriods rateAndPeriods, int timesCompounded) {
        return new CompoundInterest(rateAndPeriods, timesCompounded);
    }

    /**
     * Access a MonetaryOperator for calculation, assuming one time compounding per time period.
     *
     * @param rateAndperiods    the target rate and periods, not null.
     * @return the operator, never null.
     */
    public static CompoundInterest of(RateAndPeriods rateAndperiods) {
        return new CompoundInterest(rateAndperiods, 1);
    }

    /**
     * Performs the calculation, assuming timesCompounded/period = 1.
     *
     * @param amount  the base amount, not null.
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        return calculate(amount, rateAndPeriods, 1);
    }


        /**
         * Performs the calculation.
         *
         * @param amount  the base amount, not null.
         * @param rateAndPeriods    the target rate and periods, not null.
         * @return the resulting amount, never null.
         */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods, int timesCompounded) {
        BigDecimal part2 = rateAndPeriods.getRate().get().divide(BigDecimal.valueOf(timesCompounded), mathContext());
        BigDecimal base = one().add(part2);
        BigDecimal multiplicator = base.pow(rateAndPeriods.getPeriods() * timesCompounded, mathContext());
        return amount.multiply(multiplicator).subtract(amount);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "CompoundInterest{" +
                "\n " + rateAndPeriods +
                ",\n timesCompounded=" + timesCompounded +
                '}';
    }
}
