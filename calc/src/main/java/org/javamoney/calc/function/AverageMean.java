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

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * This class allows to calculate the average of some {@link MonetaryAmount} instances, all of the
 * same currency.
 * <p>
 * This class calculates a simple average, by
 * <ul>
 * <li>totalizing all amounts given in the {@link Iterable} passed.
 * <li>hereby counting the number
 * <li>finally dividing the total amount by the number of amounts.
 * </ul>
 * 
 * @see http://www.financeformulas.net/Average-Collection-Period.html
 * @author Anatole Tresch
 */
final class AverageMean implements
		MonetaryCalculation<Iterable<? extends MonetaryAmount>> {

	/**
	 * Private constructor, there is only one instance of this class, accessible calling
	 * {@link #of()}.
	 */
	AverageMean() {
	}

	/**
	 * Evaluates the average of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same currency.
	 * @return the average.
	 */
	public MonetaryAmount calculate(Iterable<? extends MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		CurrencyUnit unit = null;
		BigDecimal total = null;
		int itemNumber = 0;

		for (MonetaryAmount amount : amounts) {
			itemNumber++;
			if (unit == null) {
				unit = amount.getCurrency();
			}
			else if (!unit.equals(amount.getCurrency())) {
				throw new IllegalArgumentException(
						"Incompatible currencies encountered: " + unit + ", "
								+ amount.getCurrency());
			}
			if (total == null) {
				total = amount.getNumber().numberValue(BigDecimal.class);
			} else {
				total = total.add(amount.getNumber().numberValue(
						BigDecimal.class));
			}
		}
		if (total == null) {
			throw new IllegalArgumentException("No amounts to totalize.");
		}
		return Money.of(unit, total.divide(BigDecimal.valueOf(itemNumber)));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Average Mean [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
