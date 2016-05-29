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
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Growing%20Annuity%20Payment%201.gif"/>
 * <br/>
 * <p>
 * The growing annuity payment from present value formula shown above is used to calculate the
 * initial payment of a series of periodic payments that grow at a proportionate rate. This formula
 * is used specifically when present value is known.
 * <p>
 * A growing annuity is an annuity where the payments grow at a particular rate. For example, assume
 * that the initial payment is $100 and the payments are expected to grow each period at 10%. As
 * stated, the first payment is $100, then the second payment would be $110 ($100 x [1 + g]), and
 * the third payment would be $121 ($110 x [1 + g]).
 * <p>
 * It is important to keep in mind that the formula shown above will only calculate the first
 * payment.
 * 
 * @see http://www.financeformulas.net/Growing-Annuity-Payment.html
 * @author Anatole
 * @author Werner
 */
public final class PresentValueGrowingAnnuityPayment implements MonetaryOperator {

    private Rate discountRate;
    private Rate growthRate;
    private int periods;

    /**
     * Constructor.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    private PresentValueGrowingAnnuityPayment(Rate discountRate, Rate growthRate,
                                              int periods) {
        this.discountRate = Objects.requireNonNull(discountRate);
        this.growthRate = Objects.requireNonNull(growthRate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static PresentValueGrowingAnnuityPayment of(Rate discountRate, Rate growthRate, int periods) {
        return new PresentValueGrowingAnnuityPayment(discountRate, growthRate, periods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount     the dividend payment
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate discountRate, Rate growthRate,
                                           int periods) {
        Objects.requireNonNull(amount, "amount required");
        Objects.requireNonNull(discountRate, "discountRate required");
        Objects.requireNonNull(growthRate, "growthRate required");
        BigDecimal numerator = discountRate.get().subtract(growthRate.get());
        BigDecimal denum = BigDecimal.ONE.subtract(BigDecimal.ONE
                .add(growthRate.get())
                .divide(BigDecimal.ONE.add(discountRate.get()), CalculationContext.mathContext())
                .pow(periods, CalculationContext.mathContext()));
        return amount.multiply(numerator.divide(denum, CalculationContext.mathContext()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, discountRate, growthRate, periods);
    }

    @Override
    public String toString() {
        return "PresentValueGrowingAnnuityPayment{" +
                "discountRate=" + discountRate +
                ", growthRate=" + growthRate +
                ", periods=" + periods +
                '}';
    }
}
