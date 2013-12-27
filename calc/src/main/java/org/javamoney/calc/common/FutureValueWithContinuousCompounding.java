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

import javax.money.MonetaryAmount;

import com.ibm.icu.math.BigDecimal;

/**
 * <p>
 * <img
 * src="http://www.financeformulas.net/Formula%20Images/FV%20-%20Continuous%20Compounding%201.gif"
 * />
 * </p>
 * <p>
 * The future value with continuous compounding formula is used in calculating the later value of a
 * current sum of money. Use of the future value with continuous compounding formula requires
 * understanding of 3 general financial concepts, which are time value of money, future value as it
 * applies to the time value of money, and continuous compounding.
 * </p>
 * <h3>Time Value of Money, Future Value, and Continuous Compounding</h3>
 * <p>
 * <ul>
 * <li>Time Value of Money - The future value with continuous compounding formula relies on the
 * underlying concept of time value of money. Time value of money is the notion that a current sum
 * of money(or unit of account) is worth more today than the same amount at a future date.
 * <li>Future Value - Future value expands upon the idea of time value of money in that it
 * quantifies the amount required at a later date. For example, suppose that an individual has a
 * choice between receiving $1000 today or $1050 one year from today. Is the additional $50 worth
 * waiting one year for? Can the individual invest elsewhere and make a higher return? Could the
 * individual use the $1000 now for a higher "utility of enjoyment" than the $50 warrants? The
 * future value with continuous compounding formula calculates the later value when there is
 * continuous compounding.
 * <li>Continuous Compounding - Continuous compounding is compounding that is in constant motion as
 * opposed to incremental steps. Continuous compounding is considered to have an infinite amount of
 * compounding periods for a certain period of time because there is no incremental steps as found
 * in monthly or annual compounding.
 * </ul>
 * Particularly the last 2 of these concepts lends to the actual formula for future value with
 * continuous compounding.
 * </p>
 * 
 * @author Anatole Tresch
 */
public final class FutureValueWithContinuousCompounding extends AbstractPeriodicalFunction{

	private static final FutureValueWithContinuousCompounding INSTANCE = new FutureValueWithContinuousCompounding();

	private FutureValueWithContinuousCompounding() {
	}

	public static final FutureValueWithContinuousCompounding of() {
		return INSTANCE;
	}
	
	@Override
	public MonetaryAmount calculate(MonetaryAmount amount, Rate rate,
			int periods) {
		MonetaryAmount pv = PresentValue.of().calculate(amount, rate, periods);
		BigDecimal fact = new BigDecimal(String.valueOf(Math.pow(Math.E, rate.get().doubleValue() * periods)));
		return pv.multiply(fact);
	}

}
