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
 * The future value of an annuity formula is used to calculate what the value at
 * a future date would be for a series of periodic payments. The future value of
 * an annuity formula assumes that
 * 
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value
 * of each individual cash flow would need to be calculated to determine the
 * future value of the annuity. If the first cash flow, or payment, is made
 * immediately, the future value of annuity due formula would be used.
 * 
 * @see http://www.financeformulas.net/Present_Value_of_Annuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public class PresentValueOfAnnuity implements MonetaryOperator,
		CompoundCalculation<MonetaryAmount> {

	private Rate rate;
	private int periods;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(PresentValueOfAnnuity.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("amount", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public PresentValueOfAnnuity(Rate rate, int periods) {
		if (rate == null) {
			throw new IllegalArgumentException("rate null.");
		}
		this.rate = rate;
		this.periods = periods;
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		// Am * (((1 + r).pow(n))-1/rate)
		return Money.from(amount).multiply(BigDecimal.ONE.add(rate.get()).pow(periods)
				.subtract(BigDecimal.ONE).divide(rate.get()));
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
		return Money.from(amount).multiply(BigDecimal.ONE.add(rate.get()).pow(periods)
				.subtract(BigDecimal.ONE).divide(rate.get()));
	}
}
