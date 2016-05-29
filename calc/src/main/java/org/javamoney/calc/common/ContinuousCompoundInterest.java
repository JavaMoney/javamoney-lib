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

import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;

import java.math.BigDecimal;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Continuous%20Compounding%201.gif" />
 * <p>
 * The continuous compounding formula is used to determine the interest earned on an account that is
 * constantly compounded, essentially leading to an infinite amount of compounding periods. The
 * effect of compounding is earning interest on an investment, or at times paying interest on a
 * debt, that is reinvested to earn additional monies that would not have been gained based on the
 * principal balance alone. By earning interest on prior interest, one can earn at an exponential
 * rate. The continuous compounding formula takes this effect of compounding to the furthest limit.
 * Instead of compounding interest on an monthly, quarterly, or annual basis, continuous compounding
 * will effectively reinvest gains perpetually.
 *
 * @author Anatole
 * @author Werner
 */
public final class ContinuousCompoundInterest implements MonetaryOperator {

    /**
     * the target rate, not null.
     */
    private final Rate rate;
    /**
     * the periods, >= 0.
     */
    private final int periods;

    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private ContinuousCompoundInterest(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the operator, never null.
     */
    public static ContinuousCompoundInterest of(Rate rate, int periods) {
        return new ContinuousCompoundInterest(rate, periods);
    }

    public int getPeriods() {
        return periods;
    }

    public Rate getRate() {
        return rate;
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the base amount, not null.
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods) {
        return CompoundInterest.calculate(amount, rate, periods, 10000);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "ContinuousCompoundInterest{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
