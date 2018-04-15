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

import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * The formula for the present value of an annuity due,
 * sometimes referred to as an immediate annuity, is used to calculate a series of periodic
 * payments, or cash flows, that start immediately.
 *
 * @author Anatole Tresch TODO Check test values!!!
 * @see <a href="http://www.financeformulas.net/Present_Value_of_Annuity_Due.html">http://www.financeformulas.net/Present_Value_of_Annuity_Due.html</a>
 */
public final class PresentValueOfAnnuityDue extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private PresentValueOfAnnuityDue(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The rate and periods, not null.
     * @return the operator, never null.
     */
    public static PresentValueOfAnnuityDue of(RateAndPeriods rateAndPeriods) {
        return new PresentValueOfAnnuityDue(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount         the first payment
     * @param rateAndPeriods The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rateAndPeriods, "RateAndPeriods required");
        if(rateAndPeriods.getPeriods()==0){
            return amount.getFactory().setNumber(0.0).create();
        }else if(rateAndPeriods.getPeriods()==1){
            return amount;
        }
        return PresentValueOfAnnuity.calculate(amount, rateAndPeriods)
                .multiply(rateAndPeriods.getRate().get().add(CalculationContext.one()));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "PresentValueOfAnnuityDue{" +
                "\n " + rateAndPeriods +
                '}';
    }
}
