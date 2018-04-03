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

import org.javamoney.calc.CalculationContext;

import java.math.BigDecimal;
import java.util.Objects;

import javax.money.MonetaryAmount;

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
 * @link http://www.financeformulas.net/Growing-Annuity-Payment.html
 * @author Anatole
 * @author Werner
 */
public final class PresentValueGrowingAnnuityPayment extends AbstractRateAndPeriodBasedOperator {

    private final Rate growthRate;

    /**
     * Constructor.
     *
     * @param discountRateAndPeriods The discount rate and target periods, not null.
     * @param growthRate   The growth rate, not null.
     */
    private PresentValueGrowingAnnuityPayment(RateAndPeriods discountRateAndPeriods, Rate growthRate) {
        super(discountRateAndPeriods);
        this.growthRate = Objects.requireNonNull(growthRate);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param discountRateAndPeriods The discount rate and periods, not null.
     * @param growthRate   The growth rate, not null.
     * @return the operator, never null.
     */
    public static PresentValueGrowingAnnuityPayment of(RateAndPeriods discountRateAndPeriods, Rate growthRate) {
        return new PresentValueGrowingAnnuityPayment(discountRateAndPeriods, growthRate);
    }

    /**
     * Performs the calculation.
     *
     * @param amount     the dividend payment
     * @param discountRateAndPeriods The discount rate and periods, not null.
     * @param growthRate   The growth rate, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods discountRateAndPeriods, Rate growthRate) {
        Objects.requireNonNull(amount, "amount required");
        Objects.requireNonNull(discountRateAndPeriods, "discountRateAndPeriods required");
        Objects.requireNonNull(growthRate, "growthRate required");
        Rate discountRate = discountRateAndPeriods.getRate();
        int periods = discountRateAndPeriods.getPeriods();
        BigDecimal numerator = discountRate.get().subtract(growthRate.get());
        BigDecimal denum = BigDecimal.ONE.subtract(BigDecimal.ONE
                .add(growthRate.get())
                .divide(BigDecimal.ONE.add(discountRate.get()), CalculationContext.mathContext())
                .pow(periods, CalculationContext.mathContext()));
        return amount.multiply(numerator.divide(denum, CalculationContext.mathContext()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods, growthRate);
    }

    @Override
    public String toString() {
        return "PresentValueGrowingAnnuityPayment{" +
                "discountRateAndPeriods=" + rateAndPeriods +
                ", growthRate=" + growthRate +
                '}';
    }
}
