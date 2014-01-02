/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. Contributors: Anatole Tresch - initial implementation Werner Keil - extensions and
 * adaptions.
 */
package org.javamoney.calc;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import javax.money.CurrencyUnit;
//import javax.money.MonetaryAmount;
//import javax.money.MonetaryAmountFactory;
//import javax.money.MonetaryContext;
//import javax.money.MonetaryCurrencies;
//import javax.money.MonetaryOperator;
//import javax.money.MonetaryQuery;
//import javax.money.NumberValue;
//
//import org.javamoney.moneta.FastMoney;
//import org.javamoney.moneta.Money;
//import org.javamoney.moneta.spi.AbstractMoney;
//import org.javamoney.moneta.spi.DefaultNumberValue;
//
///**
// * <type>double</type> based implementation of {@link MonetaryAmount}. This class internally uses a
// * single double number as numeric representation.<br/>
// * It suggested to have a performance advantage of a 3-5 times faster compared to {@link Money}, and
// * about twice faster compared to {@link FastMoney}, which internally uses {@link BigDecimal}.
// * Nevertheless this comes with a price of less precision. As an example performing the following
// * calculation one million times, results in slightly different results:
// * 
// * <pre>
// * Money money1 = money1.add(Money.of(EURO, 1234567.3444));
// * money1 = money1.subtract(Money.of(EURO, 232323));
// * money1 = money1.multiply(3.4);
// * money1 = money1.divide(5.456);
// * </pre>
// * 
// * Executed one million (1000000) times this results in {@code EUR 1657407.962529182}, calculated in
// * 3680 ms, or roughly 3ns/loop.
// * <p>
// * whrereas
// * 
// * <pre>
// * FastMoney money1 = money1.add(FastMoney.of(EURO, 1234567.3444));
// * money1 = money1.subtract(FastMoney.of(EURO, 232323));
// * money1 = money1.multiply(3.4);
// * money1 = money1.divide(5.456);
// * </pre>
// * 
// * executed one million (1000000) times results in {@code EUR 1657407.96251}, calculated in 179 ms,
// * which is less than 1ns/loop.
// * <p>
// * Also note than mixing up types my drastically change the performance behavior. E.g. replacing the
// * code above with the following: *
// * 
// * <pre>
// * FastMoney money1 = money1.add(Money.of(EURO, 1234567.3444));
// * money1 = money1.subtract(FastMoney.of(EURO, 232323));
// * money1 = money1.multiply(3.4);
// * money1 = money1.divide(5.456);
// * </pre>
// * 
// * executed one million (1000000) times may execute significantly longer, since monetary amount type
// * conversion is involved.
// * 
// * @version 0.5.1
// * @author Anatole Tresch
// * @author Werner Keil
// */
//public final class UltraFastMoney extends AbstractMoney implements
//		Comparable<MonetaryAmount>, Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	/** The numeric part of this amount. */
//	private double number;
//
//	/** the {@link MonetaryContext} used by this instance, e.g. on division. */
//	private static final MonetaryContext MONETARY_CONTEXT = new MonetaryContext.Builder(
//			UltraFastMoney.class)
//			.setMaxScale(32).setFixedScale(false)
//			.setPrecision(String.valueOf(Double.MAX_VALUE).length() - 1)
//			.build();
//
//	private static final ThreadLocal<StringBuilder> builders = new ThreadLocal<StringBuilder>() {
//		@Override
//		protected StringBuilder initialValue() {
//			return new StringBuilder();
//		}
//	};
//
//	/**
//	 * Required for deserialization only.
//	 */
//	private UltraFastMoney() {
//	}
//
//	/**
//	 * Creates a new instance os {@link UltraFastMoney}.
//	 * 
//	 * @param currency
//	 *            the currency, not null.
//	 * @param number
//	 *            the amount, not null.
//	 */
//	private UltraFastMoney(CurrencyUnit currency, Number number) {
//		super(currency, MONETARY_CONTEXT);
//		Objects.requireNonNull(number, "Number is required.");
//		this.number = number.doubleValue();
//	}
//
//	/**
//	 * Creates a new instance os {@link UltraFastMoney}.
//	 * 
//	 * @param currency
//	 *            the currency, not null.
//	 * @param number
//	 *            the amount, not null.
//	 */
//	private UltraFastMoney(CurrencyUnit currency, NumberValue numberBinding) {
//		super(currency, MONETARY_CONTEXT);
//		Objects.requireNonNull(numberBinding, "Number is required.");
//		this.number = numberBinding.doubleValue();
//	}
//
//	private UltraFastMoney(CurrencyUnit currency, long number) {
//		super(currency, MONETARY_CONTEXT);
//		Objects.requireNonNull(currency, "Currency is required.");
//		this.currency = currency;
//		this.number = number;
//	}
//
//	/**
//	 * Static factory method for creating a new instance of {@link UltraFastMoney}.
//	 * 
//	 * @param currency
//	 *            The target currency, not null.
//	 * @param numberBinding
//	 *            The numeric part, not null.
//	 * @return A new instance of {@link UltraFastMoney}.
//	 */
//	public static UltraFastMoney of(CurrencyUnit currency,
//			NumberValue numberBinding) {
//		String numString = numberBinding.toString();
//		return new UltraFastMoney(currency, numberBinding);
//	}
//
//	/**
//	 * Static factory method for creating a new instance of {@link UltraFastMoney}.
//	 * 
//	 * @param currency
//	 *            The target currency, not null.
//	 * @param number
//	 *            The numeric part, not null.
//	 * @return A new instance of {@link UltraFastMoney}.
//	 */
//	public static UltraFastMoney of(CurrencyUnit currency, Number number) {
//		return new UltraFastMoney(currency, number);
//	}
//
//	/**
//	 * Static factory method for creating a new instance of {@link UltraFastMoney}.
//	 * 
//	 * @param currencyCode
//	 *            The target currency as currency code.
//	 * @param number
//	 *            The numeric part, not null.
//	 * @return A new instance of {@link UltraFastMoney}.
//	 */
//	public static UltraFastMoney of(String currencyCode, Number number) {
//		return new UltraFastMoney(
//				MonetaryCurrencies.getCurrency(currencyCode),
//				number);
//	}
//
////	/**
////	 * Factory method creating a zero instance with the given {@code currency);
////	 * @param currency the target currency of the amount being created.
////	 * @return
////	 */
////	public static UltraFastMoney ofZero(CurrencyUnit currency) {
////		UltraFastMoney cached = getFromCache(currency, "0");
////		if (cached != null) {
////			return cached;
////		}
////		UltraFastMoney fm = new UltraFastMoney(currency, 0L);
////		storeInCache(currency, "0", fm);
////		return fm;
////	}
////
/////**
////	 * Factory method creating a zero instance with the given {@code currency);
////	 * @param currency the target currency of the amount being created.
////	 * @return
////	 */
////	public static UltraFastMoney ofZero(String currencyCode) {
////		CurrencyUnit unitUnit = MonetaryCurrencies.getCurrency(currencyCode);
////		UltraFastMoney cached = getFromCache(unitUnit, "0");
////		if (cached != null) {
////			return cached;
////		}
////		UltraFastMoney fm = new UltraFastMoney(unitUnit, 0L);
////		storeInCache(unitUnit, "0", fm);
////		return fm;
////	}
//
//	/*
//	 * @see java.lang.Comparable#compareTo(java.lang.Object)
//	 */
//	public int compareTo(MonetaryAmount o) {
//		int compare = this.currency.getCurrencyCode().compareTo(
//				o.getCurrency().getCurrencyCode());
//		if (compare == 0) {
//			double otherNum = o.getNumber().doubleValue();
//			if (this.number < otherNum) {
//				return -1;
//			}
//			else if (this.number > otherNum) {
//				return 1;
//			}
//			return 0;
//		}
//		return compare;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((currency == null) ? 0 : currency.hashCode());
//		result = prime * result + (int) number;
//		return result;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UltraFastMoney other = (UltraFastMoney) obj;
//		if (currency == null) {
//			if (other.getCurrency() != null)
//				return false;
//		} else if (!currency.equals(other.getCurrency()))
//			return false;
//		if (number != other.number)
//			return false;
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#getCurrency()
//	 */
//	public CurrencyUnit getCurrency() {
//		return currency;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#abs()
//	 */
//	public UltraFastMoney abs() {
//		if (this.isPositiveOrZero()) {
//			return this;
//		}
//		return this.negate();
//	}
//
//	// Arithmetic Operations
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
//	 */
//	public UltraFastMoney add(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return new UltraFastMoney(getCurrency(), this.number
//				+ UltraFastMoney.from(amount).number);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
//	 */
//	public UltraFastMoney divide(Number divisor) {
//		checkNumber(divisor);
//		return new UltraFastMoney(getCurrency(), Math.round(this.number
//				/ divisor.doubleValue()));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
//	 */
//	public UltraFastMoney[] divideAndRemainder(Number divisor) {
//		checkNumber(divisor);
//		BigDecimal div = getBigDecimal(divisor);
//		BigDecimal[] res = getNumber().numberValue(BigDecimal.class)
//				.divideAndRemainder(div);
//		return new UltraFastMoney[] {
//				new UltraFastMoney(getCurrency(), res[0]),
//				new UltraFastMoney(getCurrency(), res[1]) };
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
//	 */
//	public UltraFastMoney divideToIntegralValue(Number divisor) {
//		checkNumber(divisor);
//		BigDecimal div = getBigDecimal(divisor);
//		return new UltraFastMoney(getCurrency(), getNumber().numberValue(
//				BigDecimal.class)
//				.divideToIntegralValue(div));
//	}
//
//	public UltraFastMoney multiply(Number multiplicand) {
//		checkNumber(multiplicand);
//		double multiplicandNum = multiplicand.doubleValue();
//		return new UltraFastMoney(getCurrency(),
//				Math.round(this.number * multiplicandNum));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#negate()
//	 */
//	public UltraFastMoney negate() {
//		return new UltraFastMoney(getCurrency(), this.number * -1);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#plus()
//	 */
//	public UltraFastMoney plus() {
//		if (this.number >= 0) {
//			return this;
//		}
//		return new UltraFastMoney(getCurrency(), this.number * -1);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
//	 */
//	public UltraFastMoney subtract(MonetaryAmount subtrahend) {
//		checkAmountParameter(subtrahend);
//		if (UltraFastMoney.from(subtrahend).isZero()) {
//			return this;
//		}
//		return new UltraFastMoney(getCurrency(), this.number
//				- UltraFastMoney.from(subtrahend).number);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
//	 */
//	public UltraFastMoney remainder(Number divisor) {
//		checkNumber(divisor);
//		return new UltraFastMoney(getCurrency(), this.number
//				% divisor.doubleValue());
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
//	 */
//	public UltraFastMoney scaleByPowerOfTen(int n) {
//		return new UltraFastMoney(getCurrency(), getNumber().numberValue(
//				BigDecimal.class)
//				.scaleByPowerOfTen(n));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isZero()
//	 */
//	public boolean isZero() {
//		return this.number == 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isPositive()
//	 */
//	public boolean isPositive() {
//		return this.number > 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
//	 */
//	public boolean isPositiveOrZero() {
//		return this.number >= 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isNegative()
//	 */
//	public boolean isNegative() {
//		return this.number < 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
//	 */
//	public boolean isNegativeOrZero() {
//		return this.number <= 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#getScale()
//	 */
//	public int getScale() {
//		return new BigDecimal(String.valueOf(number)).scale();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#getPrecision()
//	 */
//	public int getPrecision() {
//		if (this.number < 0) {
//			return String.valueOf(this.number).length() - 1;
//		}
//		return String.valueOf(this.number).length();
//	}
//
//	public long longValue() {
//		return (long) this.number;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#longValueExact()
//	 */
//	public long longValueExact() {
//		if ((this.number % 1) == 0) {
//			return (long) this.number;
//		}
//		throw new ArithmeticException("Amount has fractions: " + this);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#signum()
//	 */
//
//	public int signum() {
//		if (this.number < 0) {
//			return -1;
//		}
//		if (this.number == 0) {
//			return 0;
//		}
//		return 1;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#lessThan(javax.money.MonetaryAmount)
//	 */
//	public boolean isLessThan(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return this.number < amount.getNumber().doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#lessThan(java.lang.Number)
//	 */
//	public boolean isLessThan(Number number) {
//		checkNumber(number);
//		return this.number < number.doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#lessThanOrEqualTo(javax.money.MonetaryAmount)
//	 */
//	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return this.number <= amount.getNumber().doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#lessThanOrEqualTo(java.lang.Number)
//	 */
//	public boolean isLessThanOrEqualTo(Number number) {
//		checkNumber(number);
//		return this.number <= number.doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#greaterThan(javax.money.MonetaryAmount)
//	 */
//	public boolean isGreaterThan(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return this.number > UltraFastMoney.from(amount).number;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#greaterThan(java.lang.Number)
//	 */
//	public boolean isGreaterThan(Number number) {
//		checkNumber(number);
//		return this.number > number.doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#greaterThanOrEqualTo(javax.money.MonetaryAmount ) #see
//	 */
//	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return this.number >= amount.getNumber().doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#greaterThanOrEqualTo(java.lang.Number)
//	 */
//	public boolean isGreaterThanOrEqualTo(Number number) {
//		checkNumber(number);
//		return this.number >= number.doubleValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
//	 */
//	public boolean isEqualTo(MonetaryAmount amount) {
//		checkAmountParameter(amount);
//		return this.number == UltraFastMoney.from(amount).number;
//	}
//
//	/**
//	 * Gets the number representation of the numeric value of this item.
//	 * 
//	 * @return The {@link Number} represention matching best.
//	 */
//	@Override
//	public NumberValue getNumber() {
//		return new DefaultNumberValue(getBigDecimal());
//	}
//
//	// Static Factory Methods
//	/**
//	 * Translates a {@code BigDecimal} value and a {@code CurrencyUnit} currency into a
//	 * {@code Money}.
//	 * 
//	 * @param number
//	 *            numeric value of the {@code Money}.
//	 * @param currency
//	 *            currency unit of the {@code Money}.
//	 * @return a {@code Money} combining the numeric value and currency unit.
//	 */
//	public static UltraFastMoney of(CurrencyUnit currency, BigDecimal number) {
//		return new UltraFastMoney(currency, number);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return currency.toString() + ' ' + getBigDecimal();
//	}
//
//	// Internal helper methods
//
//	/**
//	 * Internal method to check for correct number parameter.
//	 * 
//	 * @param number
//	 * @throws IllegalArgumentException
//	 *             If the number is null
//	 */
//	public void checkNumber(Number number) {
//		Objects.requireNonNull(number, "Number is required.");
//	}
//
//	/*
//	 * }(non-Javadoc)
//	 * @see javax.money.MonetaryAmount#adjust(javax.money.AmountAdjuster)
//	 */
//	@Override
//	public UltraFastMoney with(MonetaryOperator adjuster) {
//		return (UltraFastMoney) adjuster.apply(this);
//	}
//
//	@Override
//	public <R> R query(MonetaryQuery<R> query) {
//		return query.queryFrom(this);
//	}
//
//	public static UltraFastMoney from(MonetaryAmount amount) {
//		if (UltraFastMoney.class == amount.getClass()) {
//			return (UltraFastMoney) amount;
//		}
//		else if (Money.class == amount.getClass()) {
//			return new UltraFastMoney(amount.getCurrency(),
//					amount.getNumber());
//		}
//		return new UltraFastMoney(amount.getCurrency(),
//				amount.getNumber());
//	}
//
//	private BigDecimal getBigDecimal() {
//		return new BigDecimal(String.valueOf(this.number));
//	}
//
//	@Override
//	public MonetaryContext getMonetaryContext() {
//		return MONETARY_CONTEXT;
//	}
//
//	@Override
//	public UltraFastMoney multiply(double amount) {
//		return of(getCurrency(), amount * number);
//	}
//
//	@Override
//	public UltraFastMoney divide(long number) {
//		return of(getCurrency(), this.number / number);
//	}
//
//	@Override
//	public UltraFastMoney divide(double number) {
//		return of(getCurrency(), this.number / number);
//	}
//
//	@Override
//	public UltraFastMoney remainder(long number) {
//		return remainder(new BigDecimal(number));
//	}
//
//	@Override
//	public UltraFastMoney remainder(double amount) {
//		return remainder(new BigDecimal(String.valueOf(amount)));
//	}
//
//	@Override
//	public UltraFastMoney[] divideAndRemainder(long amount) {
//		return divideAndRemainder(BigDecimal.valueOf(amount));
//	}
//
//	@Override
//	public UltraFastMoney[] divideAndRemainder(double amount) {
//		return divideAndRemainder(new BigDecimal(String.valueOf(amount)));
//	}
//
//	@Override
//	public UltraFastMoney stripTrailingZeros() {
//		return this;
//	}
//
//	@Override
//	public UltraFastMoney multiply(long multiplicand) {
//		return of(getCurrency(), this.number * multiplicand);
//	}
//
//	@Override
//	public UltraFastMoney divideToIntegralValue(long divisor) {
//		return divideToIntegralValue(getBigDecimal(divisor));
//	}
//
//	@Override
//	public UltraFastMoney divideToIntegralValue(double divisor) {
//		return divideToIntegralValue(getBigDecimal(divisor));
//	}
//
//	@Override
//	protected MonetaryContext getDefaultMonetaryContext() {
//		return MONETARY_CONTEXT;
//	}
//
//	@Override
//	public MonetaryAmountFactory<UltraFastMoney> getFactory() {
//		return new UltraFastMoneyAmountFactory().setAmount(this);
//	}
//
//}
