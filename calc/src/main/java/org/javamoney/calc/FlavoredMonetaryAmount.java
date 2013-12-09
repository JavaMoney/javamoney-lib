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
package org.javamoney.calc;

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;
import javax.money.MonetaryQuery;

import org.javamoney.moneta.Money;

/**
 * This class allows to attach an {@link String} to the {@link MonetaryAmount}
 * instance for identifying the type of amount during prosecuted calculation
 * steps.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class FlavoredMonetaryAmount implements MonetaryAmount, CurrencySupplier {

	private static final String UNKNOWN = "N/A";

	private MonetaryAmount baseAmount;
	private String amountType;

	public FlavoredMonetaryAmount(MonetaryAmount baseAmount,
			String amountType) {
		if (baseAmount == null) {
			throw new IllegalArgumentException("baseAmount required.");
		}
		if (amountType == null) {
			throw new IllegalArgumentException("amountType required.");
		}
		this.baseAmount = baseAmount;
		this.amountType = amountType;
	}

	/**
	 * Get the underlying base {@link MonetaryAmount}, which is never an
	 * instance of {@link FlavoredMonetaryAmount}.
	 * 
	 * @return the base amount, never {@code null}.
	 */
	public MonetaryAmount getBaseAmount() {
		return this.baseAmount;
	}

	/**
	 * Get the underlying {@link String}.
	 * 
	 * @return the flavor of this amount, never {@code null}.
	 */
	public String getAmountFlavor() {
		return this.amountType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getCurrency()
	 */
	@Override
	public CurrencyUnit getCurrency() {
		return this.baseAmount.getCurrency();
	}

	/**
	 * @see #from(Number)
	 * @param number
	 *            The number
	 * @param newFlavor
	 *            the {@link String} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount with(Number number,
			String newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount)
					.with(number, newFlavor);
		}
		return new FlavoredMonetaryAmount(Money.from(this.baseAmount).with(
				number),
				newFlavor);
	}

	/**
	 * @see #from(Number)
	 * @param newFlavor
	 *            the {@link String} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount with(
			String newFlavor) {
		return new FlavoredMonetaryAmount(this.baseAmount,
				newFlavor);
	}

	/**
	 * @see #with(MonetaryOperator)
	 * @param operator
	 *            The operator
	 * @param newFlavor
	 *            the {@link String} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount with(MonetaryOperator operator,
			String newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).with(operator,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.with(operator),
				newFlavor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public FlavoredMonetaryAmount with(MonetaryOperator operator) {
		return with(operator, UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((amountType == null) ? 0 : amountType.hashCode());
		result = prime * result
				+ ((baseAmount == null) ? 0 : baseAmount.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlavoredMonetaryAmount other = (FlavoredMonetaryAmount) obj;
		if (amountType == null) {
			if (other.amountType != null)
				return false;
		} else if (!amountType.equals(other.amountType))
			return false;
		if (baseAmount == null) {
			if (other.baseAmount != null)
				return false;
		} else if (!baseAmount.equals(other.baseAmount))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + '[' + this.amountType + ']';
	}

	@Override
	public long getAmountWhole() {
		return this.baseAmount.getAmountWhole();
	}

	@Override
	public long getAmountFractionNumerator() {
		return this.baseAmount.getAmountFractionNumerator();
	}

	@Override
	public long getAmountFractionDenominator() {
		return this.baseAmount.getAmountFractionDenominator();
	}

	@Override
	public <R> R query(MonetaryQuery<R> query) {
		return this.baseAmount.query(query);
	}
}
