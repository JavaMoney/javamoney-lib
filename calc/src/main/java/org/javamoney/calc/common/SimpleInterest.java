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

import javax.money.MonetaryAmount;
import javax.money.MonetaryAdjuster;
import javax.money.Money;

import org.javamoney.function.CompoundFunction;
import org.javamoney.function.CompoundType;
import org.javamoney.function.CompoundValue;

public class SimpleInterest implements MonetaryAdjuster,
		CompoundFunction<MonetaryAmount> {

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
		factor = BigDecimal.ONE.add(rate.getRate().multiply(
				BigDecimal.valueOf(periods)));
	}

	/**
	 * The factor to be multiplied to get the new value from an amount a, where
	 * {@code f(a) = factor * a}.
	 * 
	 * @return the simple interest value factor based on the given rate and
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
	public Rate getRate() {
		return rate;
	}

	/**
	 * Calculates the future value ov {@code value}. The value hereby represents
	 * the cash flow at period 0, which is upcounted for n periods.
	 */
	@Override
	public MonetaryAmount adjustInto(MonetaryAmount amount) {
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
		Rate rate = input.get("rate", Rate.class);
		int period = input.get("periods", Integer.class);
		MonetaryAmount amount = input.get("amount", MonetaryAmount.class);
		BigDecimal factor = BigDecimal.ONE.add(rate.getRate().multiply(
				BigDecimal.valueOf(periods)));
		return Money.from(amount).multiply(factor);
	}

}
