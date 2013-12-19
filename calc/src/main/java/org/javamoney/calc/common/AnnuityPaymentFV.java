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
 * The annuity payment formula shown above is used to calculate the cash flows
 * of an annuity when future value is known. An annuity is denoted as a series
 * of periodic payments. The annuity payment formula shown here is specifically
 * used when the future value is known, as opposed to the annuity payment
 * formula used when present value is known. There are not only mathematical
 * differences between calculating an annuity when present value is known and
 * when future value is known, but also differences in the real life application
 * of the formulas. For example, if an individual is wanting to calculate the
 * payments on a loan, the original loan balance would be considered the present
 * value and the payment formula would accommodate this known variable. However,
 * if an individual is wanting to calculate how much they need to save per year
 * in an interest bearing account to have a certain balance after a specific
 * period of time, then this wanted balance would be considered the future
 * value. The latter example would use the annuity payment using future value
 * formula as the balance is increasing instead of decreasing:
 * <p>
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/Annuity%20Payment%20(FV)%201.gif"
 * />
 * <p>
 * or...
 * 
 * <pre>
 * FV(r) / (((1 + r).pow(n)) - 1)
 * </pre>
 * 
 * @see http://www.financeformulas.net/Annuity-Payment-from-Future-Value.html
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class AnnuityPaymentFV implements MonetaryOperator
{

	private Rate rate;
	private int periods;

	private static final Function FUNCTION = new Function();

	public AnnuityPaymentFV(Rate rate, int periods) {
		if (rate == null) {
			throw new IllegalArgumentException("rate null.");
		}
		this.rate = rate;
		this.periods = periods;
	}

	@Override
	public <T extends MonetaryAmount> T apply(T amount){
		// FV(r) / (((1 + r).pow(n))-1)
		return (T)FUNCTION.calculate(rate, periods, amount);
	}

	private static final class Function implements
			CompoundCalculation<MonetaryAmount> {

		private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
				.withIdForInput(AnnuityPaymentFV.class)
				.withRequiredArg("rate", Rate.class)
				.withRequiredArg("periods", Integer.class)
				.withRequiredArg("amount", MonetaryAmount.class).build();

		@Override
		public CompoundType getInputTape() {
			return INPUT_TYPE;
		}

		@Override
		public Class getResultType() {
			return MonetaryAmount.class;
		}

		@Override
		public MonetaryAmount calculate(CompoundValue input) {
			INPUT_TYPE.checkInput(input);
			int periods = input.get("periods", Integer.class);
			Rate rate = input.get("rate", Rate.class);
			MonetaryAmount amt = input.get("amount", MonetaryAmount.class);
			return calculate(rate, periods, amt);
		}

		private MonetaryAmount calculate(Rate rate, int periods,
				MonetaryAmount amt) {
			FutureValue fv = new FutureValue(rate, periods);
			return fv.apply(amt).divide(
					BigDecimal.ONE.add(rate.get()).pow(periods)
							.subtract(BigDecimal.ONE)
					);
		}
	}

}
