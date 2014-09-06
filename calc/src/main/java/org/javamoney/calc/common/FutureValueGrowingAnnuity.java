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
 * <img src="http://www.financeformulas.net/Formula%20Images/Growing%20Annuity%20-%20FV%201.gif"/>
 * <br>
 * <p>
 * The formula for the future value of a growing annuity is used to calculate the future amount of a
 * series of cash flows, or payments, that grow at a proportionate rate. A growing annuity may
 * sometimes be referred to as an increasing annuity.
 * <p>
 * <h3>Example of FV of Growing Annuity</h3>
 * <p>
 * An example of the future value of a growing annuity formula would be an individual who is paid
 * biweekly and decides to save one of her extra paychecks per year. One of her net paychecks
 * amounts to $2,000 for the first year and she expects to receive a 5% raise on her net pay every
 * year. For this example, we will use 5% on her net pay and not involve taxes and other adjustments
 * in order to hold all other things constant. In an account that has a yield of 3% per year, she
 * would like to calculate her savings balance after 5 years.
 * <p>
 * The growth rate in this example would be the 5% increase per year, the initial cash flow or
 * payment would be $2,000, the number of periods would be 5 years, and rate per period would be 3%.
 * Using these variables in the future value of growing annuity formula would show
 * <p>
 * <i>Example</i>
 * <p>
 * After solving this equation, the amount after the 5th cash flow would be $11,700.75.
 * 
 * @see http://www.financeformulas.net/Future-Value-of-Growing-Annuity.html
 * @author Anatole Tresch
 */
public final class FutureValueGrowingAnnuity {

	private static final FutureValueGrowingAnnuity INSTANCE = new FutureValueGrowingAnnuity();

	private FutureValueGrowingAnnuity() {
	}

	public static final FutureValueGrowingAnnuity of() {
		return INSTANCE;
	}

	public MonetaryAmount calculate(MonetaryAmount firstPayment,
			Rate discountRate, Rate growthRate,
			int periods) {
		BigDecimal num = BigDecimal.ONE.add(discountRate.get()).pow(periods)
				.subtract(BigDecimal.ONE.add(growthRate.get()).pow(periods));
		BigDecimal denum = discountRate.get().subtract(growthRate.get());
		return firstPayment.multiply(num.divide(denum));
	}

}
