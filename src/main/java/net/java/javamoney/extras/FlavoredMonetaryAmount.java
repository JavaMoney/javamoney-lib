/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors: Anatole Tresch - initial version.
 */
package net.java.javamoney.extras;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * This class allows to attach an {@link MonetaryAmountFlavor} to the
 * {@link MonetaryAmount} instance for identifying the type of amount during
 * prosecuted calculation steps.
 * 
 * @author Anatole Tresch
 */
public final class FlavoredMonetaryAmount implements MonetaryAmount {

	private MonetaryAmount baseAmount;
	private MonetaryAmountFlavor amountType;

	public FlavoredMonetaryAmount(MonetaryAmount baseAmount,
			MonetaryAmountFlavor amountType) {
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
	 * Get the underlying {@link MonetaryAmountFlavor}.
	 * 
	 * @return the flavor of this amount, never {@code null}.
	 */
	public MonetaryAmountFlavor getAmountFlavor() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#abs()
	 */
	@Override
	public FlavoredMonetaryAmount abs() {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).abs();
		}
		return new FlavoredMonetaryAmount(this.baseAmount.abs(),
				MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	@Override
	public FlavoredMonetaryAmount add(MonetaryAmount augend) {
		return add(augend,
				MonetaryAmountFlavor.UNKNOWN);
	}

	/**
	 * @see #add(MonetaryAmount)
	 * @param augend
	 *            The augend
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount add(MonetaryAmount augend,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).add(augend, newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.add(augend),
				newFlavor);
	}

	/**
	 * @see #divide(Number)
	 * @param divisor
	 *            The divisor
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount divide(Number divisor,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).divide(divisor,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.divide(divisor),
				newFlavor);
	}

	/**
	 * @see #divideAndRemainder(Number)
	 * @param divisor
	 *            The divisor
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount[] divideAndRemainder(Number divisor,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount)
					.divideAndRemainder(divisor, newFlavor);
		}
		MonetaryAmount[] baseResult = this.baseAmount
				.divideAndRemainder(divisor);
		FlavoredMonetaryAmount[] result = new FlavoredMonetaryAmount[baseResult.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new FlavoredMonetaryAmount(baseResult[i],
					newFlavor);
		}
		return result;
	}

	/**
	 * @see #divideToIntegralValue(Number)
	 * @param divisor
	 *            The divisor
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount divideToIntegralValue(Number divisor,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount)
					.divideToIntegralValue(divisor, newFlavor);
		}
		return new FlavoredMonetaryAmount(
				this.baseAmount.divideToIntegralValue(divisor),
				newFlavor);
	}

	/**
	 * @see #multiply(Number)
	 * @param multiplicand
	 *            The multiplicand
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount multiply(Number multiplicand,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).multiply(multiplicand,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(
				this.baseAmount.multiply(multiplicand),
				newFlavor);
	}

	/**
	 * @see #negate()
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount negate(
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).negate(newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.negate(),
				newFlavor);
	}

	/**
	 * @see #plus()
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount plus(MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).plus(newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.plus(),
				newFlavor);
	}

	/**
	 * @see #subtract()
	 * @param subtrahend
	 *            THe subtrahend
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount subtract(MonetaryAmount subtrahend,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).subtract(subtrahend,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.subtract(subtrahend),
				newFlavor);
	}

	/**
	 * @see #pow(int)
	 * @param n
	 *            The power required
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount pow(int n, MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).pow(n, newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.pow(n),
				newFlavor);
	}

	/**
	 * @see #ulp()
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount ulp(MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).ulp(newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.ulp(),
				newFlavor);
	}

	/**
	 * @see #remainder(Number)
	 * @param divisor
	 *            The divisor
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount remainder(Number divisor,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).remainder(divisor,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.remainder(divisor),
				newFlavor);
	}

	/**
	 * @see #scaleByPowerOfTen(int)
	 * @param n
	 *            The power required
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount scaleByPowerOfTen(int n,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).scaleByPowerOfTen(n,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.scaleByPowerOfTen(n),
				newFlavor);
	}

	@Override
	public boolean isZero() {
		return this.baseAmount.isZero();
	}

	@Override
	public boolean isPositive() {
		return this.baseAmount.isZero();
	}

	@Override
	public boolean isPositiveOrZero() {
		return this.baseAmount.isPositiveOrZero();
	}

	@Override
	public boolean isNegative() {
		return this.baseAmount.isNegative();
	}

	@Override
	public boolean isNegativeOrZero() {
		return this.baseAmount.isNegativeOrZero();
	}

	/**
	 * @see #from(Number)
	 * @param number
	 *            The number
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount from(Number number,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount)
					.from(number, newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.from(number),
				newFlavor);
	}

	/**
	 * @see #with(MonetaryOperator)
	 * @param operator
	 *            The operator
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount with(MonetaryOperator operator,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).with(operator,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(this.baseAmount.with(operator),
				newFlavor);
	}

	@Override
	public int getScale() {
		return this.baseAmount.getScale();
	}

	@Override
	public int getPrecision() {
		return this.baseAmount.getPrecision();
	}

	@Override
	public int intValue() {
		return this.baseAmount.intValue();
	}

	@Override
	public int intValueExact() {
		return this.baseAmount.intValueExact();
	}

	@Override
	public long longValue() {
		return this.baseAmount.longValue();
	}

	@Override
	public long longValueExact() {
		return this.baseAmount.longValueExact();
	}

	@Override
	public float floatValue() {
		return this.baseAmount.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.baseAmount.doubleValue();
	}

	@Override
	public byte byteValue() {
		return this.baseAmount.byteValue();
	}

	@Override
	public short shortValue() {
		return this.baseAmount.shortValue();
	}

	@Override
	public short shortValueExact() {
		return this.baseAmount.shortValueExact();
	}

	@Override
	public int signum() {
		return this.baseAmount.signum();
	}

	@Override
	public boolean isLessThan(MonetaryAmount amount) {
		return this.baseAmount.isLessThan(amount);
	}

	@Override
	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
		return this.baseAmount.isLessThanOrEqualTo(amount);
	}

	@Override
	public boolean isGreaterThan(MonetaryAmount amount) {
		return this.baseAmount.isGreaterThan(amount);
	}

	@Override
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		return this.baseAmount.isGreaterThanOrEqualTo(amount);
	}

	@Override
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.baseAmount.isEqualTo(amount);
	}

	@Override
	public boolean isNotEqualTo(MonetaryAmount amount) {
		return this.baseAmount.isNotEqualTo(amount);
	}

	@Override
	public <T> T asType(Class<T> type) {
		return this.baseAmount.asType(type);
	}

	@Override
	public Class<?> getNumberType() {
		return this.baseAmount.getNumberType();
	}

	/**
	 * @see #with(MonetaryOperator)
	 * @param currency
	 *            The currency
	 * @param amount
	 *            The amount
	 * @param newFlavor
	 *            the {@link MonetaryAmountFlavor} for the result instance.
	 * @return the result, with the given flavor.
	 */
	public FlavoredMonetaryAmount from(CurrencyUnit currency, Number amount,
			MonetaryAmountFlavor newFlavor) {
		if (this.baseAmount.getClass().equals(getClass())) {
			return ((FlavoredMonetaryAmount) baseAmount).from(currency, amount,
					newFlavor);
		}
		return new FlavoredMonetaryAmount(
				this.baseAmount.from(currency, amount), newFlavor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount divide(Number divisor) {
		return divide(divisor, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount[] divideAndRemainder(Number divisor) {
		return divideAndRemainder(divisor, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount divideToIntegralValue(Number divisor) {
		return divideToIntegralValue(divisor, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount multiply(Number multiplicand) {
		return multiply(multiplicand, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	@Override
	public FlavoredMonetaryAmount negate() {
		return negate(MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	@Override
	public FlavoredMonetaryAmount plus() {
		return plus(MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	@Override
	public FlavoredMonetaryAmount subtract(MonetaryAmount subtrahend) {
		return subtract(subtrahend, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	@Override
	public FlavoredMonetaryAmount pow(int n) {
		return pow(n, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	@Override
	public FlavoredMonetaryAmount ulp() {
		return ulp(MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount remainder(Number divisor) {
		return remainder(divisor, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	@Override
	public FlavoredMonetaryAmount scaleByPowerOfTen(int n) {
		return scaleByPowerOfTen(n, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount from(Number amount) {
		return from(amount, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(javax.money.CurrencyUnit,
	 * java.lang.Number)
	 */
	@Override
	public FlavoredMonetaryAmount from(CurrencyUnit currency, Number amount) {
		return from(currency, amount, MonetaryAmountFlavor.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public FlavoredMonetaryAmount with(MonetaryOperator operator) {
		return with(operator, MonetaryAmountFlavor.UNKNOWN);
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
}
