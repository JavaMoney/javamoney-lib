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
import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Simple%20Interest%201.gif" />
 * <p>
 * The simple interest formula is used to calculate the interest accrued on a loan or savings
 * account that has simple interest. The simple interest formula is fairly simple to compute and to
 * remember as principal times rate times time. An example of a simple interest calculation would be
 * a 3 year saving account at a 10% rate with an original balance of $1000. By inputting these
 * variables into the formula, $1000 times 10% times 3 years would be $300.
 * <p>
 * Simple interest is money earned or paid that does not have compounding. Compounding is the effect
 * of earning interest on the interest that was previously earned. As shown in the previous example,
 * no amount was earned on the interest that was earned in prior years.
 * <p>
 * As with any financial formula, it is important that rate and time are appropriately measured in
 * relation to one another. If the time is in months, then the rate would need to be the monthly
 * rate and not the annual rate.
 * 
 * @see http://www.financeformulas.net/Simple_Interest.html
 * @author Anatole Tresch
 */
public final class SimpleInterest extends AbstractPeriodicalFunction {

	private static final SimpleInterest INSTANCE = new SimpleInterest();

	private SimpleInterest() {
	}

	public static final SimpleInterest of() {
		return INSTANCE;
	}

	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		Objects.requireNonNull(amount, "Amount required");
		Objects.requireNonNull(rate, "Rate required");
		BigDecimal factor = BigDecimal.ONE.add(rate.get().multiply(
				BigDecimal.valueOf(periods)));
		return amount.multiply(factor);
	}

}
