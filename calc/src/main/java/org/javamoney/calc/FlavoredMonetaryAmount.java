/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc;

import java.util.Objects;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.MonetaryOperator;
import javax.money.MonetaryQuery;

/**
 * This class allows to attach an {@link String} to the {@link MonetaryAmount}
 * instance for identifying the type of amount during prosecuted calculation
 * steps.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class FlavoredMonetaryAmount implements
		MonetaryAmount {

	private static final String UNKNOWN = "N/A";

	private MonetaryAmount baseAmount;
	private String amountType = UNKNOWN;

	public FlavoredMonetaryAmount(MonetaryAmount baseAmount) {
		Objects.requireNonNull(baseAmount, "baseAmount required.");
		this.baseAmount = baseAmount;
	}
	
	public FlavoredMonetaryAmount(MonetaryAmount baseAmount,
			String amountType) {
		Objects.requireNonNull(baseAmount, "baseAmount required.");
		this.baseAmount = baseAmount;
		if(amountType!=null){
			this.amountType = amountType;
		}
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
		if(this.amountType==null){
			return UNKNOWN;
		}
		return this.amountType;
	}

	/**
	 * Sets the current amount flavor.
	 * 
	 * @param flavor
	 *            the new flavor, or {@code null}.
	 */
	public void setAmountFlavor(String flavor) {
		this.amountType = flavor;
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
		return new FlavoredMonetaryAmount(this.baseAmount.getFactory().with(
				this.baseAmount.getCurrency()).with(
				number).create(), newFlavor);
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
	public <R> R query(MonetaryQuery<R> query) {
		return this.baseAmount.query(query);
	}

	@Override
	public int getPrecision() {
		return this.baseAmount.getPrecision();
	}

	@Override
	public int getScale() {
		return this.baseAmount.getScale();
	}

	@Override
	public MonetaryContext getMonetaryContext() {
		return this.baseAmount.getMonetaryContext();
	}

	@Override
	public Number getNumber() {
		return this.baseAmount.getNumber();
	}

	@Override
	public <N extends Number> N getNumber(Class<N> type) {
		return (N) this.baseAmount.getNumber(type);
	}

	@Override
	public <N extends Number> N getNumberExact(Class<N> type) {
		return (N) this.baseAmount.getNumberExact(type);
	}

	

	@Override
	public boolean isGreaterThan(MonetaryAmount amount) {
		return this.baseAmount.isGreaterThan(amount);
	}

	@Override
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amt) {
		return this.baseAmount.isGreaterThanOrEqualTo(amt);
	}

	@Override
	public boolean isLessThan(MonetaryAmount amt) {
		return this.baseAmount.isLessThan(amt);
	}

	@Override
	public boolean isLessThanOrEqualTo(MonetaryAmount amt) {
		return this.baseAmount.isLessThanOrEqualTo(amt);
	}

	@Override
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.baseAmount.isEqualTo(amount);
	}

	@Override
	public boolean isNegative() {
		return this.baseAmount.isNegative();
	}

	@Override
	public boolean isNegativeOrZero() {
		return this.baseAmount.isNegativeOrZero();
	}

	@Override
	public boolean isPositive() {
		return this.baseAmount.isPositive();
	}

	@Override
	public boolean isPositiveOrZero() {
		return this.baseAmount.isPositiveOrZero();
	}

	@Override
	public boolean isZero() {
		return this.baseAmount.isZero();
	}

	@Override
	public int signum() {
		return this.baseAmount.signum();
	}

	@Override
	public FlavoredMonetaryAmount add(MonetaryAmount amount) {
		return new FlavoredMonetaryAmount(this.baseAmount.add(amount),
				null);
	}

	@Override
	public FlavoredMonetaryAmount subtract(MonetaryAmount amount) {
		return new FlavoredMonetaryAmount(this.baseAmount.subtract(amount),
				null);
	}

	@Override
	public FlavoredMonetaryAmount multiply(long multiplicand) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.multiply(multiplicand), null);
	}

	@Override
	public FlavoredMonetaryAmount multiply(double multiplicand) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.multiply(multiplicand), null);
	}

	@Override
	public FlavoredMonetaryAmount multiply(Number multiplicand) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.multiply(multiplicand), null);
	}

	@Override
	public FlavoredMonetaryAmount divide(long divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.divide(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount divide(double divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.divide(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount divide(Number divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.divide(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount remainder(long divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.remainder(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount remainder(double divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.remainder(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount remainder(Number divisor) {
		return new FlavoredMonetaryAmount(this.baseAmount.remainder(divisor),
				null);
	}

	@Override
	public FlavoredMonetaryAmount[] divideAndRemainder(long divisor) {
		MonetaryAmount[] result = this.baseAmount.divideAndRemainder(divisor);
		return new FlavoredMonetaryAmount[] {
				new FlavoredMonetaryAmount(result[0], null),
				new FlavoredMonetaryAmount(result[1], null) };
	}

	@Override
	public FlavoredMonetaryAmount[] divideAndRemainder(double divisor) {
		MonetaryAmount[] result = this.baseAmount.divideAndRemainder(divisor);
		return new FlavoredMonetaryAmount[] {
				new FlavoredMonetaryAmount(result[0], null),
				new FlavoredMonetaryAmount(result[1], null) };
	}

	@Override
	public FlavoredMonetaryAmount[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] result = this.baseAmount.divideAndRemainder(divisor);
		return new FlavoredMonetaryAmount[] {
				new FlavoredMonetaryAmount(result[0], null),
				new FlavoredMonetaryAmount(result[1], null) };
	}

	@Override
	public FlavoredMonetaryAmount divideToIntegralValue(long divisor) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.divideToIntegralValue(divisor), null);
	}

	@Override
	public FlavoredMonetaryAmount divideToIntegralValue(double divisor) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.divideToIntegralValue(divisor), null);
	}

	@Override
	public FlavoredMonetaryAmount divideToIntegralValue(Number divisor) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.divideToIntegralValue(divisor), null);
	}

	@Override
	public FlavoredMonetaryAmount scaleByPowerOfTen(int power) {
		return new FlavoredMonetaryAmount(
				this.baseAmount.scaleByPowerOfTen(power), null);
	}

	@Override
	public FlavoredMonetaryAmount abs() {
		return new FlavoredMonetaryAmount(this.baseAmount.abs(), null);
	}

	@Override
	public FlavoredMonetaryAmount negate() {
		return new FlavoredMonetaryAmount(this.baseAmount.negate(), null);
	}

	@Override
	public FlavoredMonetaryAmount plus() {
		return new FlavoredMonetaryAmount(this.baseAmount.plus(), null);
	}

	@Override
	public FlavoredMonetaryAmount stripTrailingZeros() {
		return new FlavoredMonetaryAmount(this.baseAmount.stripTrailingZeros(),
				amountType);
	}

	@Override
	public MonetaryAmountFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}
