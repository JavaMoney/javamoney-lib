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

import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * The formula for the present value of an annuity due,
 * sometimes referred to as an immediate annuity, is used to calculate a series of periodic
 * payments, or cash flows, that start immediately.
 * 
 * @see http://www.financeformulas.net/Present_Value_of_Annuity_Due.html
 * @author Anatole Tresch
 */
public final class PresentValueAnnuityDue extends AbstractPeriodicalFunction {

	private static final PresentValueAnnuityDue INSTANCE = new PresentValueAnnuityDue();

	private PresentValueAnnuityDue() {
	}

	public static final PresentValueAnnuityDue of() {
		return INSTANCE;
	}

	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		Objects.requireNonNull(amount, "Amount required");
		Objects.requireNonNull(rate, "Rate required");
		return PresentValueAnnuity.of().calculate(amount, rate, periods).add(amount);
	}
}
