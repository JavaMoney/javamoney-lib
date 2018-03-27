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

import static org.javamoney.calc.CalculationContext.one;

import javax.money.MonetaryAmount;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Annuity%20Payment%20(FV)%201.gif" />
 * <p>
 * The annuity payment formula shown above is used to calculate the cash flows of an annuity when
 * future value is known. An annuity is denoted as a series of periodic payments. The annuity
 * payment formula shown here is specifically used when the future value is known, as opposed to the
 * annuity payment formula used when present value is known. There are not only mathematical
 * differences between calculating an annuity when present value is known and when future value is
 * known, but also differences in the real life application of the formulas. For example, if an
 * individual is wanting to calculate the payments on a loan, the original loan balance would be
 * considered the present value and the payment formula would accommodate this known variable.
 * However, if an individual is wanting to calculate how much they need to save per year in an
 * interest bearing account to have a certain balance after a specific period of time, then this
 * wanted balance would be considered the future value. The latter example would use the annuity
 * payment using future value formula as the balance is increasing instead of decreasing:
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @link http://www.financeformulas.net/Annuity-Payment-from-Future-Value.html
 */
final class FutureValueOfAnnuityPayment extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    FutureValueOfAnnuityPayment(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The discount rate and periods, not null.
     * @return the operator, never null.
     */
    public static FutureValueOfAnnuityPayment of(RateAndPeriods rateAndPeriods) {
        return new FutureValueOfAnnuityPayment(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rateAndPeriods    The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        return FutureValue.calculate(amount, rateAndPeriods).divide(
                one().add(rateAndPeriods.getRate().get()).pow(rateAndPeriods.getPeriods())
                        .subtract(one())
        );
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "FutureValueOfAnnuityPayment{" +
                "rateAndPeriods=" + rateAndPeriods +
                '}';
    }
}
