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

import javax.money.MonetaryAmount;

/**
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/Solve%20for%20n%20-%20Annuity%20(PV)%201.gif"/>
 * </p>
 * <p>
 * The solve for n, or number of periods, formula shown above is used to determine the number of
 * periods on an annuity using the present value, periodic payment, and periodic rate. An example of
 * what the solve for n formula tries to answer is
 * "How long will it take me to pay off a balance of $a at a rate of b% by making periodic payments of $c."
 * </p>
 * <h3>Example of Solve for n on Annuity (PV) Formula</h3>
 * <p>
 * An individual is attempting to determine how many payments would be needed if they offered
 * someone $19660 at an effective rate of 1% per month. The periodic payment needed by the
 * individual is $1,000 per month.
 * <p>
 * When considering this formula, it is important that the period used for the rate and payments
 * match. For this example, the 1% rate and periodic payment is on a monthly basis. If the term and
 * rate do not match on a 'per period' basis, then the effective rate would need to be found that
 * matches how often the payments are received. The term "effective" in effective rate implies that
 * compounding is already factored in. For example, if payments are annual, then the effective
 * annual rate is used.
 * <p>
 * After solving, the number of $1,000 payments needed is 22.
 * 
 * @see http://www.financeformulas.net/Number-of-Periods-of-Annuity-from-Present-Value.html
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class NumPeriodsOfAnnuityFromPresentAndFutureValue {

    private NumPeriodsOfAnnuityFromPresentAndFutureValue() {
    }

    public static BigDecimal calculate(MonetaryAmount annuity,
                                       MonetaryAmount paymentOrCashFlows,
                                       Rate rate, int periods) {
        MonetaryAmount pvAnnuity = PresentValueOfAnnuity.calculate(
                annuity, rate, periods);
        return new BigDecimal(String.valueOf(Math.log(BigDecimal.ONE
				.subtract(pvAnnuity
						.divide(paymentOrCashFlows.getNumber()).getNumber()
						.numberValue(BigDecimal.class)).pow(-1).doubleValue())
				/ Math.log(1 + rate.get().doubleValue())));

	}
}
