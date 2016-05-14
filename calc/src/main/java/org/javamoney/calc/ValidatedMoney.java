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

import org.javamoney.moneta.Money;

import java.util.function.Predicate;

/**
 * Platform RI: This class decorates an arbitrary {@link MonetaryAmount}
 * instance and ensure the given {@link Predicate} is always
 * {@code true}.
 * <p>
 * As required by the {@link MonetaryAmount} interface, this class is
 * <ul>
 * <li>immutable</li>
 * <li>final</li>
 * <li>thread-safe/li>
 * <li>serializable</li>
 * </ul>
 * <p>
 * As a consequence all this attributes must also be true for the
 * {@link Predicate} used.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
final class ValidatedMoney implements
        MonetaryAmount {
    /**
     * The amount's predicate.
     */
    private Predicate<MonetaryAmount> predicate;
    /**
     * The underlying amount.
     */
    private final MonetaryAmount amount;

    /**
     * Creates a new wrapper instance.
     *
     * @param amount the underlying amount, not null and not negative.
     * @throws IllegalArgumentException if the amount passed is negative.
     */
    ValidatedMoney(MonetaryAmount amount,
                   Predicate<MonetaryAmount> predicate) {
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
     * Access an {@link ValidatedMoney} based on the given
     * {@link MonetaryAmount}.
     *
     * @param amount
     * @return
     */
    private static ValidatedMoney of(MonetaryAmount amount,
                                     Predicate<MonetaryAmount> predicate) {
        return new ValidatedMoney(amount, predicate);
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
    public ValidatedMoney abs() {
        return of(this.amount.abs(), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
     */
    public ValidatedMoney add(MonetaryAmount augend) {
        return of(this.amount.add(augend), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#divide(java.lang.Number)
     */
    public ValidatedMoney divide(Number divisor) {
        return of(this.amount.divide(divisor), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
     */
    public ValidatedMoney[] divideAndRemainder(Number divisor) {
        MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
        return new ValidatedMoney[]{of(res[0], predicate),
                of(res[1], predicate)};
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
     */
    public ValidatedMoney divideToIntegralValue(Number divisor) {
        return of(this.amount.divideToIntegralValue(divisor), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
     */
    public ValidatedMoney multiply(Number multiplicand) {
        return of(this.amount.multiply(multiplicand), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#negate()
     */
    public ValidatedMoney negate() {
        return of(this.amount.negate(), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#plus()
     */
    public ValidatedMoney plus() {
        return of(this.amount.plus(), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
     */
    public ValidatedMoney subtract(Money subtrahend) {
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
    public ValidatedMoney remainder(Number divisor) {
        return of(this.amount.remainder(divisor), predicate);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
     */
    public ValidatedMoney scaleByPowerOfTen(int n) {
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
    public ValidatedMoney with(MonetaryOperator adjuster) {
        return of(this.amount.with(adjuster), predicate);
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
    public MonetaryContext getContext() {
        return this.amount.getContext();
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
    public ValidatedMoney subtract(MonetaryAmount amount) {
        return new ValidatedMoney(this.amount.subtract(amount),
                predicate);
    }

    @Override
    public ValidatedMoney multiply(long multiplicand) {
        return new ValidatedMoney(amount.multiply(multiplicand),
                predicate);
    }

    @Override
    public ValidatedMoney multiply(double multiplicand) {
        return new ValidatedMoney(amount.multiply(multiplicand),
                predicate);
    }

    @Override
    public ValidatedMoney divide(long divisor) {
        return new ValidatedMoney(amount.divide(divisor), predicate);
    }

    @Override
    public ValidatedMoney divide(double divisor) {
        return new ValidatedMoney(amount.divide(divisor), predicate);
    }

    @Override
    public ValidatedMoney remainder(long divisor) {
        return new ValidatedMoney(amount.remainder(divisor), predicate);
    }

    @Override
    public ValidatedMoney remainder(double divisor) {
        return new ValidatedMoney(amount.remainder(divisor), predicate);
    }

    @Override
    public ValidatedMoney[] divideAndRemainder(long divisor) {
        MonetaryAmount[] result = this.amount.divideAndRemainder(divisor);
        return new ValidatedMoney[]{
                new ValidatedMoney(result[0], predicate),
                new ValidatedMoney(result[1], predicate)};
    }

    @Override
    public ValidatedMoney[] divideAndRemainder(double divisor) {
        MonetaryAmount[] result = this.amount.divideAndRemainder(divisor);
        return new ValidatedMoney[]{
                new ValidatedMoney(result[0], predicate),
                new ValidatedMoney(result[1], predicate)};
    }

    @Override
    public ValidatedMoney divideToIntegralValue(long divisor) {
        return new ValidatedMoney(
                amount.divideToIntegralValue(divisor),
                predicate);
    }

    @Override
    public ValidatedMoney divideToIntegralValue(double divisor) {
        return new ValidatedMoney(
                amount.divideToIntegralValue(divisor),
                predicate);
    }

    @Override
    public ValidatedMoney stripTrailingZeros() {
        return new ValidatedMoney(amount.stripTrailingZeros(),
                predicate);
    }

    @Override
    public MonetaryAmountFactory<ValidatedMoney> getFactory() {
        return null;
        // return new ConstraintMoneyFactory(this);
    }

    @Override
    public int compareTo(MonetaryAmount o) {
        return this.amount.compareTo(o);
    }

}
