/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.common;

import java.math.BigDecimal;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

import org.javamoney.calc.function.CompoundCalculation;
import org.javamoney.calc.function.CompoundType;
import org.javamoney.calc.function.CompoundValue;
import org.javamoney.moneta.Money;

/**
 * The compound interest formula calculates the amount of interest earned on an
 * account or investment where the amount earned is reinvested. By reinvesting
 * the amount earned, an investment will earn money based on the effect of
 * compounding. Compounding is the concept that any amount earned on an
 * investment can be reinvested to create additional earnings that would not be
 * realized based on the original principal, or original balance, alone. The
 * interest on the original balance alone would be called simple interest. The
 * additional earnings plus simple interest would equal the total amount earned
 * from compound interest.
 * <p>
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/Compound%20Interest%201.gif"
 * />
 * <p>
 * or...
 * <pre>
 * factor = [(1+&lt;rate>).pow(&lt;periods>))-1
 * f(&lt;amount> = &lt;amount> * &lt;factor>
 * </ptr>
 * @see http://www.financeformulas.net/Compound_Interest.html
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class CompundInterest implements MonetaryOperator,
		CompoundCalculation<MonetaryAmount> {

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(CompundInterest.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("rate", Rate.class)
			.withRequiredArg("amount", MonetaryAmount.class).build();

	public CompundInterest(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		factor = BigDecimal.ONE.add(rate.get()).pow(periods).subtract(
				BigDecimal.ONE);
	}

	/**
	 * The factor to be multiplied to get the compounded value from an amount a,
	 * where {@code f(a) = factor * a}.
	 * 
	 * @return the compounded interest value factor based on the given rate and
	 *         period.
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
	public Rate get() {
		return rate;
	}

	/**
	 * Calculates the compounded value of {@code value}. The result hereby
	 * represents the compounded value and interest with the given rate and for
	 * n periods.
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return Money.from(amount).multiply(factor);
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
		Rate rate = input.get("rate",  Rate.class);
		int periods = input.get("periods", Integer.class);
		BigDecimal f = BigDecimal.ONE.add(rate.get()).pow(periods).subtract(
				BigDecimal.ONE);
		MonetaryAmount amount = input.get("amount",  MonetaryAmount.class);
		return Money.from(amount).multiply(f);
	}

}
