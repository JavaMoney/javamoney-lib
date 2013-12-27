/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.function;

import java.nio.channels.UnsupportedAddressTypeException;

import javax.money.MonetaryAmount;

/**
 * This class allows to calculate the weighted average of some {@link MonetaryAmount} instances, all
 * of the same currency.
 * <p>
 * 
 * @see http://www.financeformulas.net/Weighted_Average.html
 * @author Anatole Tresch
 */
final class AverageWeighted implements
		MonetaryCalculation<Iterable<? extends MonetaryAmount>> {

	/**
	 * Private constructor, there is only one instance of this class, accessible calling
	 * {@link #of()}.
	 */
	AverageWeighted() {
	}

	/**
	 * Evaluates the average of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same currency.
	 * @return the average.
	 */
	public MonetaryAmount calculate(Iterable<? extends MonetaryAmount> amounts) {
		throw new UnsupportedAddressTypeException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Average Weighted[Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
