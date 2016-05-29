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
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Simple%20Interest%201.gif" />
 * <p>
 * The simple interest formula is used to calculate the interest accrued on a loan or savings
 * account that has simple interest. The simple interest formula is fairly simple to compute and to
 * remember as principal times rate times time. An example of a simple interest calculation would be
 * a 3 year saving account at a 10% rate with an original balance of $1000. By inputting these
 * variables into the formula, $1000 times 10% times 3 years would be $300.
 * <p>
 * Simple interest is money earned or paid that does not have compounding. Compounding is the effect
 * of earning interest on the interest that was previously earned. As shown in the previous example,
 * no amount was earned on the interest that was earned in prior years.
 * <p>
 * As with any financial formula, it is important that rate and time are appropriately measured in
 * relation to one another. If the time is in months, then the rate would need to be the monthly
 * rate and not the annual rate.
 * 
 * @see http://www.financeformulas.net/Simple_Interest.html
 * @author Anatole Tresch
 */
public final class SimpleInterest implements MonetaryOperator {

    private Rate rate;
    private int periods;

    private SimpleInterest(Rate rate,
                           int periods) {
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
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static SimpleInterest of(Rate rate, int periods) {
        return new SimpleInterest(rate, periods);
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
        if(periods==0 || amount.signum()==0){
            return amount.getFactory().setNumber(0.0).create();
        }
        BigDecimal factor = rate.get().multiply(
                BigDecimal.valueOf(periods), CalculationContext.mathContext());
        return amount.multiply(factor);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleInterest that = (SimpleInterest) o;

        if (periods != that.periods) return false;
        if (!rate.equals(that.rate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rate.hashCode();
        result = 31 * result + periods;
        return result;
    }

    @Override
    public String toString() {
        return "SimpleInterest{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
