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
 * <img
 * src="http://www.financeformulas.net/Formula%20Images/Number%20of%20Periods%20-%20FV-PV%201.gif"
 * /> <br/>
 * <p>
 * The formula for solving for the number of periods shown at the top of this page is used to
 * calculate the length of time required for a single cash flow(present value) to reach a certain
 * amount(future value) based on the time value of money. In other words, this formula is used to
 * calculate the length of time a present value would need to reach the future value, given a
 * certain interest rate.
 * <p>
 * The formula for solving for number of periods may also be referred to as solving for n, solving
 * for term, or solving for time. Solving for n originates from the present value and future value
 * formulas in which the variable n denotes the number of periods. It is important to keep in mind
 * that the number of periods and periodic rate should match one another. For example, if the rate
 * is compounded monthly, then the monthly rate would be used and the number of periods would
 * reflect the number of months.
 * </P>
 * <H3>Example of Solve for Number of Periods Formula (PV & FV)</H3>
 * <p>
 * An example of solving for the number of periods formula would be an individual who would like to
 * determine how long it would take for his $1500 balance in his account to reach $2000 in an
 * account that pays 6% interest, compounded monthly. Of course, for this example it is assumed that
 * there will be no deposits nor withdrawals within this timeframe.
 * <p>
 * As previously stated in the prior section, the number of periods and the periodic rate should
 * match one another. The 6% annual interest rate is compounded monthly, so .005(equal to .5%) would
 * be used for r as this is the monthly rate.
 * <p>
 * For this example, the equation to solve for the number of periods would result in 57.68 months.
 * Of course in real situations the fraction of a month may not be exact due to when the account is
 * credited, there may be charges to the account that must be accounted for, and so on.
 * <p>
 * This can be checked by putting these variables into the present value formula and confirming that
 * in fact there will be a $2000 balance after 57.68 months based on a monthly rate of .5%.
 * 
 * @see http://www.financeformulas.net/Solve-for-Number-of-Periods-PV-and-FV.html
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class NumPeriodsFromPresentValue {

    private NumPeriodsFromPresentValue() {
    }

    public static BigDecimal calculate(MonetaryAmount presentValue,
                                       MonetaryAmount futureValue, Rate rate, int periods) {
		double result = Math.log(futureValue.divide(presentValue.getNumber())
				.getNumber().doubleValue())
				/ Math.log(1 + rate.get().doubleValue());
		return new BigDecimal(String.valueOf(result));
	}
}
