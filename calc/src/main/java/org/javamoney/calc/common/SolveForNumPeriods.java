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

import javax.money.MonetaryAdjuster;
import javax.money.MonetaryAmount;
import javax.money.Money;

import org.javamoney.function.CompoundFunction;
import org.javamoney.function.CompoundType;
import org.javamoney.function.CompoundValue;
import org.javamoney.function.MonetaryFunction;

/**
 * The formula for solving for the number of periods shown at the top of this
 * page is used to calculate the length of time required for a single cash
 * flow(present value) to reach a certain amount(future value) based on the time
 * value of money. In other words, this formula is used to calculate the length
 * of time a present value would need to reach the future value, given a certain
 * interest rate. The formula for solving for number of periods may also be
 * referred to as solving for n, solving for term, or solving for time. Solving
 * for n originates from the present value and future value formulas in which
 * the variable n denotes the number of periods. It is important to keep in mind
 * that the number of periods and periodic rate should match one another. For
 * example, if the rate is compounded monthly, then the monthly rate would be
 * used and the number of periods would reflect the number of months.
 * 
 * @see http
 *      ://www.financeformulas.net/Solve-for-Number-of-Periods-PV-and-FV.html
 * @author Anatole Tresch
 */
public class SolveForNumPeriods implements MonetaryFunction<Rate, BigDecimal>
{

	private MonetaryAmount presentValue;
	private MonetaryAmount futureValue;
	private int periods;

	private static final Function FUNCTION = new Function();

	public SolveForNumPeriods(MonetaryAmount presentValue,
			MonetaryAmount futureValue, int periods) {
		if (presentValue == null) {
			throw new IllegalArgumentException("presentValue required.");
		}
		if (futureValue == null) {
			throw new IllegalArgumentException("futureValue required.");
		}
		this.presentValue = presentValue;
		this.futureValue = futureValue;
	}

	/**
	 * @return the presentValue
	 */
	public final MonetaryAmount getPresentValue() {
		return presentValue;
	}

	/**
	 * @return the futureValue
	 */
	public final MonetaryAmount getFutureValue() {
		return futureValue;
	}

	/**
	 * @return the periods
	 */
	public final int getPeriods() {
		return periods;
	}

	public BigDecimal calculate(Rate rate) {
		MonetaryAmount pv = new PresentValue(rate, periods)
				.adjustInto(presentValue);
		MonetaryAmount fv = new PresentValue(rate, periods)
				.adjustInto(futureValue);
		return FUNCTION.calculate(rate, periods, pv, fv);
	}

	public static CompoundFunction<BigDecimal> getFunction() {
		return FUNCTION;
	}

	private static final class Function implements CompoundFunction<BigDecimal> {

		private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
				.withIdForInput(SimpleInterest.class)
				.withRequiredArg("periods", Integer.class)
				.withRequiredArg("presentValue", MonetaryAmount.class)
				.withRequiredArg("futureValue", MonetaryAmount.class)
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
			Rate rate = input.get("rate", Rate.class);
			int periods = input.get("periods", Integer.class);
			MonetaryAmount pv = input.get("presentValue", MonetaryAmount.class);
			MonetaryAmount fv = input.get("futureValue", MonetaryAmount.class);
			return calculate(rate, periods, pv, fv);
		}
		
		private BigDecimal calculate(Rate rate, int periods, MonetaryAmount presentValue, MonetaryAmount futureValue){
			BigDecimal count = BigDecimal.valueOf(Math.log(Money.from(futureValue)
					.doubleValue() / Money.from(presentValue).doubleValue()));
			BigDecimal divisor = BigDecimal.valueOf(Math.log(1 + rate.getRate()
					.doubleValue()));
			return count.divide(divisor);
		}
	}
}
