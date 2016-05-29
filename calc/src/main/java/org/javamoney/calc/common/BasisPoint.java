/*
 *  Copyright (c) 2012, 2013, Trivadis (Anatole Tresch), Werner Keil.
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
package org.javamoney.calc.common;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

import org.javamoney.calc.CalculationContext;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;


/**
 * This class allows to extract the BasisPoint of a {@link MonetaryAmount} instance.
 * 
 * @version 0.5
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Per_mil">Wikipedia: Per mil</a>
 */
public final class BasisPoint implements MonetaryOperator {

	private static final BigDecimal ONE_TENTHOUSAND = new BigDecimal(10000,
			CalculationContext.mathContext());

	private final BigDecimal basisPointValue;

	/**
	 * Access the shared instance of {@link BasisPoint} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	private BasisPoint(final Number decimal) {
		basisPointValue = calcBasisPoint(decimal);
	}

	/**
	 * Factory method creating a new instance with the given {@code Number) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static BasisPoint of(Number number) {
		return new BasisPoint(number);
	}

	/**
	 * Gets the permil of the amount.
	 * <p>
	 * This returns the monetary amount in permil. For example, for 10% 'EUR
	 * 2.35' will return 0.235.
	 * <p>
	 * This is returned as a {@code MonetaryAmount}.
	 * 
	 * @return the permil result of the amount, never {@code null}
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return Money.from(amount).multiply(basisPointValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return NumberFormat.getInstance()
				.format(
						basisPointValue.multiply(ONE_TENTHOUSAND,
                                CalculationContext.mathContext()))
				+
				"\u2031";
	}

	/**
	 * Gets the permil of the amount.
	 * <p>
	 * This returns the monetary amount in permil. For example, for 10% 'EUR
	 * 2.35' will return 0.235.
	 * <p>
	 * This is returned as a {@code MonetaryAmount}.
	 *
	 * @return the permil result of the amount, never {@code null}
	 */
	public static MonetaryAmount calculate(MonetaryAmount amount, Number basisPoints) {
		return amount.multiply(calcBasisPoint(basisPoints));
	}


	/**
	 * Calculate a BigDecimal value for a Permil e.g. "3" (3 permil) will
	 * generate .003
	 * 
	 * @return java.math.BigDecimal
	 * @param number
	 *            the basis points number, 10'000-ends.
	 */
	private static final BigDecimal calcBasisPoint(Number number) {
		return MoneyUtils.getBigDecimal(number).divide(
				ONE_TENTHOUSAND, CalculationContext.mathContext());
	}

}
