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

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Compound%20Interest%201.gif" />
 * <p>
 * The compound interest formula calculates the amount of interest earned on an account or
 * investment where the amount earned is reinvested. By reinvesting the amount earned, an investment
 * will earn money based on the effect of compounding. Compounding is the concept that any amount
 * earned on an investment can be reinvested to create additional earnings that would not be
 * realized based on the original principal, or original balance, alone. The interest on the
 * original balance alone would be called simple interest. The additional earnings plus simple
 * interest would equal the total amount earned from compound interest.
 * 
 * @see http://www.financeformulas.net/Compound_Interest.html
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class CompoundInterest extends AbstractPeriodicalFunction {

	private static final CompoundInterest INSTANCE = new CompoundInterest();

	private CompoundInterest() {
	}

	public static final CompoundInterest getInstance() {
		return INSTANCE;
	}

	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		BigDecimal f = BigDecimal.ONE.add(rate.get()).pow(periods).subtract(
				BigDecimal.ONE);
		return amount.multiply(f);
	}

}
