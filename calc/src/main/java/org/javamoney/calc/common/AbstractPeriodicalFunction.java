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
 * Base implementation for {@link PeriodicalFunction}.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractPeriodicalFunction implements PeriodicalFunction {

	@Override
	public MonetaryOperator getOperator(Rate rate, int periods) {
		return new MonetaryOperatorAdapter(rate, periods, this);
	}

	private static final class MonetaryOperatorAdapter implements
			MonetaryOperator {
		private Rate rate;
		private int periods;
		private PeriodicalFunction function;

		public MonetaryOperatorAdapter(Rate rate, int periods,
				PeriodicalFunction function) {
			Objects.requireNonNull(rate);
			Objects.requireNonNull(function);
			if (periods < 0) {
				throw new IllegalArgumentException("Periods < 0.");
			}
			this.rate = rate;
			this.periods = periods;
		}

		@Override
		public MonetaryAmount apply(MonetaryAmount amount) {
			return function.calculate(amount, rate, periods);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MonetaryOperatorAdapter:" + " [function="
					+ function.getClass() + ", rate=" + rate + ", periods="
					+ periods + "]";
		}

	}

}
