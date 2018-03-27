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

import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Present%20Value%203.gif" />
 * <p>
 * Present Value (PV) is a formula used in Finance that calculates the present day value of an
 * amount that is received at a future date. The premise of the equation is that there is
 * "time value of money".
 * <p>
 * Time value of money is the concept that receiving something today is worth more than receiving
 * the same item at a future date. The presumption is that it is preferable to receive $100 today
 * than it is to receive the same amount one year from today, but what if the choice is between $100
 * present day or $106 a year from today? A formula is needed to provide a quantifiable comparison
 * between an amount today and an amount at a future time, in terms of its present day value.
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Present%20Value%201.gif" />
 * <p>
 * alterantively this can be written also as (which is much easier to implement):<br/>
 *
 * @author Anatole Tresch
 * @link http://www.financeformulas.net/Present_Value.html
 * @link http://www.financeformulas.net/Present_Value.html
 */
public final class PresentValue extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private PresentValue(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The discount rate and periods, not null.
     * @return the operator, never null.
     */
    public static PresentValue of(RateAndPeriods rateAndPeriods) {
        return new PresentValue(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rateAndPeriods    The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods){
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rateAndPeriods, "RateAndPeriods required");
        return amount.divide(PresentValueFactor.calculate(rateAndPeriods));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "PresentValue{" +
                "\n " + rateAndPeriods +
                '}';
    }
}
