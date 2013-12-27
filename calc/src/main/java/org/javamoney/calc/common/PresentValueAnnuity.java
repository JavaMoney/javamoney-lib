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
import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * The future value of an annuity formula is used to calculate what the value at a future date would
 * be for a series of periodic payments. The future value of an annuity formula assumes that
 * 
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value of each individual
 * cash flow would need to be calculated to determine the future value of the annuity. If the first
 * cash flow, or payment, is made immediately, the future value of annuity due formula would be
 * used.
 * 
 * @see http://www.financeformulas.net/Present_Value_of_Annuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public final class PresentValueAnnuity extends AbstractPeriodicalFunction {
	private static final PresentValueAnnuity INSTANCE = new PresentValueAnnuity();

	private PresentValueAnnuity() {
	}

	public static final PresentValueAnnuity of() {
		return INSTANCE;
	}
	
	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		Objects.requireNonNull(amount, "Amount required");
		Objects.requireNonNull(rate, "Rate required");
		return amount.multiply(BigDecimal.ONE.add(rate.get()).pow(periods)
				.subtract(BigDecimal.ONE).divide(rate.get()));
	}
}
