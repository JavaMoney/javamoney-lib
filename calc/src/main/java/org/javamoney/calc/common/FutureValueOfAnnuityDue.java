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

import javax.money.MonetaryAmount;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Annuity%20Due%20-%20FV%201.gif" />
 * <p>
 * The future value of annuity due formula is used to calculate the ending value of a series of
 * payments or cash flows where the first payment is received immediately. The first cash flow
 * received immediately is what distinguishes an annuity due from an ordinary annuity. An annuity
 * due is sometimes referred to as an immediate annuity.
 * <p>
 * The future value of annuity due formula calculates the value at a future date. The use of the
 * future value of annuity due formula in real situations is different than that of the present
 * value for an annuity due. For example, suppose that an individual or company wants to buy an
 * annuity from someone and the first payment is received today. To calculate the price to pay for
 * this particular situation would require use of the present value of annuity due formula. However,
 * if an individual is wanting to calculate what their balance would be after saving for 5 years in
 * an interest bearing account and they choose to put the first cash flow into the account today,
 * the future value of annuity due would be used.
 * 
 * @see http://www.financeformulas.net/Future-Value-of-Annuity-Due.html
 * @author Anatole Tresch
 */
public final class FutureValueOfAnnuityDue extends AbstractPeriodicalFunction {

	private static final FutureValueOfAnnuityDue INSTANCE = new FutureValueOfAnnuityDue();

	private FutureValueOfAnnuityDue() {
	}

	public static final FutureValueOfAnnuityDue of() {
		return INSTANCE;
	}

	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		// Am * (((1 + r).pow(n))-1/rate)
		return PresentValueAnnuity.of().calculate(amount, rate, periods)
				.add(amount);
	}

}
