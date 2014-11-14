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

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

/**
 * <p>
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/Solve%20for%20n%20-%20Annuity%20(FV)%201.gif" />
 * <br/>
 * <p>
 * The formula for solving for number of periods (n) on an annuity shown above is used to calculate
 * the number of periods based on the future value, rate, and periodic cash flows.
 * <p>
 * The formula at the top of the page, solving for n, generally approaches the question
 * "How long will it take to save $x amount dollars by saving $y per month at a rate of z%". <7p>
 * <h3>Example of Solve for n on Annuity Formula</h3>
 * <p>
 * Suppose that an individual receives an additional $1000 pay or bonus semiannually. Suppose this
 * individual would like to find out how long until they save $19600 by saving $1,000 every half
 * year at an effective rate of 5% every half year. **It is important to remember that the rate
 * should match the frequency of the cash flows/payments. For example, if cash flows are
 * semi-annual, then the effective semi-annual rate is used. The term effective implies that
 * compounding is already adjusted for that period (see effective rate).
 * <p>
 * Using the formula at the top of the page to solve for the number of periods, n, for this example
 * would show the equation
 * <p>
 * Example of Solving for n results in 14 semi-annual periods.
 * 
 * @see http://www.financeformulas.net/Number-of-Periods-of-Annuity-from-Future-Value.html
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class NumPeriodsOfAnnuityFromFutureValue {

    private NumPeriodsOfAnnuityFromFutureValue() {
    }

    /**
     * See above.
     *
     * @param paymentOrCashFlows the payment or cash flows.
     * @param futureValue        the future value
     * @param rate               the target rate
     * @param periods            the number of periods
     * @return the number of periods.
     */
    public static BigDecimal calculate(MonetaryAmount paymentOrCashFlows,
                                       MonetaryAmount futureValue, Rate rate, int periods) {
		double num = Math.log(BigDecimal.ONE.add(
				futureValue.multiply(rate.get()).divide(
						paymentOrCashFlows.getNumber()).getNumber()
						.numberValue(BigDecimal.class)).doubleValue());
		double denum = Math.log(BigDecimal.ONE.add(rate.get()).doubleValue());
		return new BigDecimal(String.valueOf(num / denum));
	}
}
