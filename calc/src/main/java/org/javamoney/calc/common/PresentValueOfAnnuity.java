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

import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * The future value of an annuity formula is used to calculate what the value at a future date would
 * be for a series of periodic payments. The future value of an annuity formula assumes that
 * 
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value of each individual
 * cash flow would need to be calculated to determine the future value of the annuity. If the first
 * cash flow, or payment, is made immediately, the future value of annuity due formula would be
 * used.
 * 
 * @link http://www.financeformulas.net/Present_Value_of_Annuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public final class PresentValueOfAnnuity extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private PresentValueOfAnnuity(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The rate and periods, not null.
     * @return the operator, never null.
     */
    public static PresentValueOfAnnuity of(RateAndPeriods rateAndPeriods) {
        return new PresentValueOfAnnuity(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rateAndPeriods    The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rateAndPeriods, "Rate required");
        MonetaryAmount sum = null;
        Rate rate = rateAndPeriods.getRate();
        int periods = rateAndPeriods.getPeriods();
        for(int i=1;i<=periods;i++){
            MonetaryAmount temp = PresentValue.calculate(amount, RateAndPeriods.of(rate, i));
            if(sum==null){
                sum = temp;
            }else {
                sum = sum.add(temp);
            }
        }
        if(sum==null){
            return amount.getFactory().setNumber(0).create();
        }
        return sum;
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "PresentValueOfAnnuity{" +
                "\n " + rateAndPeriods +
                '}';
    }
}
