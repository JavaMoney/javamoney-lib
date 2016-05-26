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
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Perpetuity1.gif"/> <br/>
 * A perpetuity is a type of annuity that receives an infinite amount of periodic payments. An
 * annuity is a financial instrument that pays consistent periodic payments. As with any annuity,
 * the perpetuity value formula sums the present value of future cash flows.
 * <p>
 * Common examples of when the perpetuity value formula is used is in consols issued in the UK and
 * preferred stocks. Preferred stocks in most circumstances receive their dividends prior to any
 * dividends paid to common stocks and the dividends tend to be fixed, and in turn, their value can
 * be calculated using the perpetuity formula.
 * <p>
 * The value of a perpetuity can change over time even though the payment remains the same. This
 * occurs as the discount rate used may change. If the discount rate used lowers, the denominator of
 * the formula lowers, and the value will increase.
 * <p>
 * It should be noted that the formula shown supposes that the cash flows per period never change.
 * 
 * @see http://www.financeformulas.net/Perpetuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public final class PresentValueOfPerpetuity implements MonetaryOperator {

    private Rate rate;

    private PresentValueOfPerpetuity(Rate rate) {
        this.rate = Objects.requireNonNull(rate);
    }

    /**
     * Get the rate of this operator instance.
     * @return the rate, never null.
     */
    public Rate getRate() {
        return rate;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate The rate, not null.
     * @return the operator, never null.
     */
    public static PresentValueOfPerpetuity of(Rate rate) {
        return new PresentValueOfPerpetuity(rate);
    }

    /**
     * Performs the calculation.
     *
     * @param amount the first payment
     * @param rate   The rate, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rate, "Rate required");
        return amount.divide(rate.get());
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate);
    }

    @Override
    public String toString() {
        return "PresentValueOfPerpetuity{" +
                "rate=" + rate +
                '}';
    }
}
