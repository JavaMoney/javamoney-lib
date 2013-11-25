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

import org.javamoney.calc.function.CompoundCalculation;
import org.javamoney.calc.function.CompoundType;
import org.javamoney.calc.function.CompoundValue;

/**
 * 
 * @author Anatole
 * 
 */
public class DiscountFactor {

	private Rate rate;

	private static final Function FUNCTION = new Function();

	private DiscountFactor(Rate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("rate required.");
		}
		this.rate = rate;
	}

	public static DiscountFactor of(Rate rate) {
		return new DiscountFactor(rate);
	}

	public Rate get() {
		return rate;
	}

	public BigDecimal calculate(Integer periods) {
		// (1-(1+r)^n)/1-(1+rate)
		BigDecimal div = BigDecimal.ONE
				.min(BigDecimal.ONE.add(rate.get()));
		BigDecimal factor = BigDecimal.ONE.subtract(
				BigDecimal.ONE.add(rate.get()).pow(periods)).divide(div);
		return BigDecimal.ONE.add(factor);
	}

	public static CompoundCalculation<BigDecimal> getFunction() {
		return FUNCTION;
	}

	private static final class Function implements
			CompoundCalculation<BigDecimal> {
		private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
				.withIdForInput(DiscountFactor.class)
				.withRequiredArg("periods", Integer.class)
				.withRequiredArg("rate", Rate.class).build();

		@Override
		public CompoundType getInputTape() {
			return INPUT_TYPE;
		}

		@Override
		public Class<BigDecimal> getResultType() {
			return BigDecimal.class;
		}

		@Override
		public BigDecimal calculate(CompoundValue input) {
			INPUT_TYPE.checkInput(input);
			Integer periods = input.get("periods", Integer.class);
			Rate rate = input.get("rate", Rate.class);
			BigDecimal div = BigDecimal.ONE
					.min(BigDecimal.ONE.add(rate.get()));
			BigDecimal factor = BigDecimal.ONE.subtract(
					BigDecimal.ONE.add(rate.get()).pow(periods))
					.divide(div);
			return BigDecimal.ONE.add(factor);
		}
	}

}
