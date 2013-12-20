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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.MonetaryOperator;
import javax.money.MonetaryQuery;
import javax.money.NumberValue;

import org.javamoney.calc.function.MonetaryPredicate;
import org.javamoney.moneta.Money;

/**
 * Platform RI: This class decorates an arbitrary {@link MonetaryAmount}
 * instance and ensure the given {@link MonetaryPredicate} is always
 * {@code true}.
 * <p>
 * As required by the {@link MonetaryAmount} interface, this class is
 * <ul>
 * <li>immutable</li>
 * <li>final</li>
 * <li>thread-safe/li>
 * <li>serializable</li>
 * </ul>
 * 
 * As a consequence all this attributes must also be true for the
 * {@link MonetaryPredicate} used.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
final class ConstraintMoney implements
		MonetaryAmount {
	/** The amount's predicate. */
	private MonetaryPredicate<MonetaryAmount> predicate;
	/** The underlying amount. */
	private final MonetaryAmount amount;

	/**
	 * Creates a new wrapper instance.
	 * 
	 * @param amount
	 *            the underlying amount, not null and not negative.
	 * @throws IllegalArgumentException
	 *             if the amount passed is negative.
	 */
	ConstraintMoney(MonetaryAmount amount,
			MonetaryPredicate<MonetaryAmount> predicate) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		if (predicate == null) {
			throw new IllegalArgumentException("predicate required.");
		}
		if (!predicate.test(amount)) {
			throw new IllegalArgumentException("Constraint failed: "
					+ predicate + " with " + amount);
		}
		this.amount = amount;
		this.predicate = predicate;
	}

	/**
	 * Access an {@link ConstraintMoney} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 * @return
	 */
	private static ConstraintMoney of(MonetaryAmount amount,
			MonetaryPredicate<MonetaryAmount> predicate) {
		return new ConstraintMoney(amount, predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getCurrency()
	 */
	@Override
	public CurrencyUnit getCurrency() {
		return this.amount.getCurrency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#abs()
	 */
	public ConstraintMoney abs() {
		return of(this.amount.abs(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	public ConstraintMoney add(MonetaryAmount augend) {
		return of(this.amount.add(augend), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	public ConstraintMoney divide(Number divisor) {
		return of(this.amount.divide(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	public ConstraintMoney[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new ConstraintMoney[] { of(res[0], predicate),
				of(res[1], predicate) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	public ConstraintMoney divideToIntegralValue(Number divisor) {
		return of(this.amount.divideToIntegralValue(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
	 */
	public ConstraintMoney multiply(Number multiplicand) {
		return of(this.amount.multiply(multiplicand), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	public ConstraintMoney negate() {
		return of(this.amount.negate(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	public ConstraintMoney plus() {
		return of(this.amount.plus(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	public ConstraintMoney subtract(Money subtrahend) {
		return of(this.amount.subtract(subtrahend), predicate);
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see javax.money.MonetaryAmount#ulp()
	// */
	// public ConstraintMoney ulp() {
	// return of(this.amount.ulp(), predicate);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	public ConstraintMoney remainder(Number divisor) {
		return of(this.amount.remainder(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	public ConstraintMoney scaleByPowerOfTen(int n) {
		return of(this.amount.scaleByPowerOfTen(n), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	public boolean isZero() {
		return this.amount.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	public boolean isPositive() {
		return this.amount.isPositive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	public boolean isPositiveOrZero() {
		return this.amount.isPositiveOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	public boolean isNegative() {
		return this.amount.isNegative();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	public boolean isNegativeOrZero() {
		return this.amount.isNegativeOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public ConstraintMoney with(MonetaryOperator adjuster) {
		return of(this.amount.with(adjuster), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	public int getScale() {
		return this.amount.getScale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	public int getPrecision() {
		return this.amount.getPrecision();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */
	public int signum() {
		return this.amount.signum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isLessThan(javax.money.MonetaryAmount)
	 */
	public boolean isLessThan(MonetaryAmount amount) {
		return this.amount.isLessThan(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#isLessThanOrEqualTo(javax.money.MonetaryAmount
	 * )
	 */
	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isLessThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isGreaterThan(javax.money.MonetaryAmount)
	 */
	public boolean isGreaterThan(MonetaryAmount amount) {
		return this.amount.isGreaterThan(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#isGreaterThanOrEqualTo(javax.money.MonetaryAmount
	 * )
	 */
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isGreaterThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.amount.isEqualTo(amount);
	}

	@Override
	public MonetaryContext getMonetaryContext() {
		return this.amount.getMonetaryContext();
	}

	@Override
	public NumberValue getNumber() {
		return this.amount.getNumber();
	}

	@Override
	public <R> R query(MonetaryQuery<R> query) {
		return query.queryFrom(this);
	}

	@Override
	public ConstraintMoney subtract(MonetaryAmount amount) {
		return new ConstraintMoney(this.amount.subtract(amount),
				predicate);
	}

	@Override
	public ConstraintMoney multiply(long multiplicand) {
		return new ConstraintMoney(amount.multiply(multiplicand),
				predicate);
	}

	@Override
	public ConstraintMoney multiply(double multiplicand) {
		return new ConstraintMoney(amount.multiply(multiplicand),
				predicate);
	}

	@Override
	public ConstraintMoney divide(long divisor) {
		return new ConstraintMoney(amount.divide(divisor), predicate);
	}

	@Override
	public ConstraintMoney divide(double divisor) {
		return new ConstraintMoney(amount.divide(divisor), predicate);
	}

	@Override
	public ConstraintMoney remainder(long divisor) {
		return new ConstraintMoney(amount.remainder(divisor), predicate);
	}

	@Override
	public ConstraintMoney remainder(double divisor) {
		return new ConstraintMoney(amount.remainder(divisor), predicate);
	}

	@Override
	public ConstraintMoney[] divideAndRemainder(long divisor) {
		MonetaryAmount[] result = this.amount.divideAndRemainder(divisor);
		return new ConstraintMoney[] {
				new ConstraintMoney(result[0], predicate),
				new ConstraintMoney(result[1], predicate) };
	}

	@Override
	public ConstraintMoney[] divideAndRemainder(double divisor) {
		MonetaryAmount[] result = this.amount.divideAndRemainder(divisor);
		return new ConstraintMoney[] {
				new ConstraintMoney(result[0], predicate),
				new ConstraintMoney(result[1], predicate) };
	}

	@Override
	public ConstraintMoney divideToIntegralValue(long divisor) {
		return new ConstraintMoney(
				amount.divideToIntegralValue(divisor),
				predicate);
	}

	@Override
	public ConstraintMoney divideToIntegralValue(double divisor) {
		return new ConstraintMoney(
				amount.divideToIntegralValue(divisor),
				predicate);
	}

	@Override
	public ConstraintMoney stripTrailingZeros() {
		return new ConstraintMoney(amount.stripTrailingZeros(),
				predicate);
	}

	@Override
	public MonetaryAmountFactory<ConstraintMoney> getFactory() {
		return new ConstraintMoneyFactory(this).with(this);
	}

}
