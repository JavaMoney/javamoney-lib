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

/**
 * The annuity payment formula is used to calculate the periodic payment on an annuity. An annuity
 * is a series of periodic payments that are received at a future date. The present value portion of
 * the formula is the initial payout, with an example being the original payout on an amortized
 * loan.
 * <p>
 * The annuity payment formula shown is for ordinary annuities. This formula assumes that the rate
 * does not change, the payments stay the same, and that the first payment is one period away. An
 * annuity that grows at a proportionate rate would use the growing annuity payment formula.
 * Otherwise, an annuity that changes the payment and/or rate would need to be adjusted for each
 * change. An annuity that has its first payment due at the beginning would use the annuity due
 * payment formula and the deferred annuity payment formula would have a payment due at a later
 * date.
 * <p>
 * The annuity payment formula can be used for amortized loans, income annuities, structured
 * settlements, lottery payouts(see annuity due payment formula if first payment starts
 * immediately), and any other type of constant periodic payments.
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%201.gif" />
 * <p>
 * This can be rewritten as:<br/>
 * http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%203.gif
 * <p>
 * or...
 * 
 * <pre>
 * AP(m) = PV(m,r,n) / [ (1-((1 + r).pow(-n))) / r ]
 * </pre>
 * 
 * @see http://www.financeformulas.net/Annuity_Payment_Formula.html
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class AnnuityPaymentPV implements MonetaryOperator
{

	private Rate rate;
	private int periods;

	private static final Function FUNCTION = new Function();

	public AnnuityPaymentPV(Rate rate, int periods) {
		if (rate == null) {
			throw new IllegalArgumentException("rate null.");
		}
		this.rate = rate;
		this.periods = periods;
	}

	@Override
	public <T extends MonetaryAmount> T apply(T amount) {
		// FV(r) / (((1 + r).pow(n))-1)
		return (T) FUNCTION.calculate(rate, periods, amount);
	}

	private static final class Function implements
			CompoundCalculation<MonetaryAmount> {

		private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
				.withIdForInput(AnnuityPaymentPV.class)
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
			// AP(m) = PV(m,r,n) / [ (1-((1 + r).pow(-n))) / r ]
			PresentValue pv = new PresentValue(rate, periods);
			return pv.apply(amt).divide(
					BigDecimal.ONE.subtract((BigDecimal.ONE.add(rate.get())
							.pow(-1 * periods).divide(rate.get())
							)));
		}
	}

}
