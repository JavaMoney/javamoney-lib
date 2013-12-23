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
import javax.money.MonetaryOperator;

import org.javamoney.calc.function.CompoundCalculation;
import org.javamoney.calc.function.CompoundType;
import org.javamoney.calc.function.CompoundValue;
import org.javamoney.moneta.Money;

/**
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
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Simple%20Interest%201.gif" />
 * <p>
 * or...
 * 
 * <pre>
 * &lt;amount> * &lt;rate> * &lt;periods>
 * </pre>
 * 
 * @see http://www.financeformulas.net/Simple_Interest.html
 * @author Anatole Tresch
 */
public class SimpleInterest implements MonetaryOperator,
		CompoundCalculation<MonetaryAmount> {  // ,Supplier<Rate> for Java 8/9

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(SimpleInterest.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("amount", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public SimpleInterest(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		factor = BigDecimal.ONE.add(rate.get().multiply(
				BigDecimal.valueOf(periods)));
	}

	/**
	 * The factor to be multiplied to get the new value from an amount a, where
	 * {@code f(a) = factor * a}.
	 * 
	 * @return the simple interest value factor based on the given rate and period.
	 */
	public BigDecimal getFactor() {
		return factor;
	}

	/**
	 * The number of periods.
	 * 
	 * @return the number of periods.
	 */
	public int getPeriods() {
		return periods;
	}

	/**
	 * The rate of return.
	 * 
	 * @return the rate of return.
	 */
	public Rate getRate() {
		return rate;
	}

	/**
	 * Calculates the future value ov {@code value}. The value hereby represents the cash flow at
	 * period 0, which is upcounted for n periods.
	 */
	@Override
	public <T extends MonetaryAmount> T apply(T amount) {
		return (T) amount.multiply(factor);
	}

	@Override
	public CompoundType getInputTape() {
		return INPUT_TYPE;
	}

	@Override
	public Class<MonetaryAmount> getResultType() {
		return MonetaryAmount.class;
	}

	@Override
	public MonetaryAmount calculate(CompoundValue input) {
		INPUT_TYPE.checkInput(input);
		Rate rate = input.get("rate", Rate.class);
		int period = input.get("periods", Integer.class);
		MonetaryAmount amount = input.get("amount", MonetaryAmount.class);
		BigDecimal factor = BigDecimal.ONE.add(rate.get().multiply(
				BigDecimal.valueOf(periods)));
		return amount.multiply(factor);
	}

}
