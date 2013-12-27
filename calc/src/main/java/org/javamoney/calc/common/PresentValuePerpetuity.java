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

import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Perpetuity1.gif"/> <br/>
 * A perpetuity is a type of annuity that receives an infinite amount of periodic payments. An
 * annuity is a financial instrument that pays consistent periodic payments. As with any annuity,
 * the perpetuity value formula sums the present value of future cash flows.
 * <p>
 * Common examples of when the perpetuity value formula is used is in consols issued in the UK and
 * preferred stocks. Preferred stocks in most circumstances receive their dividends prior to any
 * dividends paid to common stocks and the dividends tend to be fixed, and in turn, their value can
 * be calculated using the perpetuity formula.
 * <p>
 * The value of a perpetuity can change over time even though the payment remains the same. This
 * occurs as the discount rate used may change. If the discount rate used lowers, the denominator of
 * the formula lowers, and the value will increase.
 * <p>
 * It should be noted that the formula shown supposes that the cash flows per period never change.
 * 
 * @see http://www.financeformulas.net/Perpetuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public final class PresentValuePerpetuity {
	private static final PresentValuePerpetuity INSTANCE = new PresentValuePerpetuity();

	private PresentValuePerpetuity() {
	}

	public static final PresentValuePerpetuity of() {
		return INSTANCE;
	}

	public MonetaryAmount calculate(MonetaryAmount dividend, Rate rate) {
		return dividend.divide(rate.get());
	}

	public MonetaryOperator getOperator(Rate rate) {
		return new MonetaryOperatorAdapter(rate);
	}

	private static final class MonetaryOperatorAdapter implements
			MonetaryOperator {
		private Rate rate;

		public MonetaryOperatorAdapter(Rate rate) {
			Objects.requireNonNull(rate);
			this.rate = rate;
		}

		@Override
		public <T extends MonetaryAmount> T apply(T amount) {
			return (T) PresentValuePerpetuity.of().calculate(amount,
					rate);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MonetaryOperatorAdapter:" + " [function="
					+ PresentValuePerpetuity.class + ", rate="
					+ rate
					+ "]";
		}

	}
}
