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
package org.javamoney.calc.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;
import org.javamoney.moneta.Money;


/**
 * This class allows to extract the BasisPoint of a {@link MonetaryAmount} instance.
 * 
 * @version 0.5
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Per_mil">Wikipedia: Per mil</a>
 */
public final class BasisPoint implements MonetaryOperator,
		ComplexCalculation<ComplexType,MonetaryAmount> {

	private static final MathContext DEFAULT_MATH_CONTEXT = initDefaultMathContext();
	private static final BigDecimal ONE_TENTHOUSAND = new BigDecimal(10000,
			DEFAULT_MATH_CONTEXT);

	private final BigDecimal basisPointValue;

	private ComplexType INPUT_TYPE = new ComplexType.Builder("BasisPoint")
			.setNameForInput(BasisPoint.class)
			.addRequiredParameter("basisPoints", Number.class)
			.addRequiredParameter("amount", MonetaryAmount.class).build();

	/**
	 * Access the shared instance of {@link BasisPoint} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	private BasisPoint(final BigDecimal decimal) {
		basisPointValue = calcBasisPoint(decimal);
	}

	/**
	 * Get {@link MathContext} for {@link BasisPoint} instances.
	 * 
	 * @return the {@link MathContext} to be used, by default
	 *         {@link MathContext#DECIMAL64}.
	 */
	private static MathContext initDefaultMathContext() {
		// TODO Initialize default, e.g. by system properties, or better:
		// classpath properties!
		return MathContext.DECIMAL64;
	}

/**
	 * Factory method creating a new instance with the given {@code BigDecimal) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static BasisPoint of(BigDecimal decimal) {
		return new BasisPoint(decimal); // TODO caching, e.g. array for 1-100
										// might
		// work.
	}

/**
	 * Factory method creating a new instance with the given {@code Number) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static BasisPoint of(Number number) {
		return of(getBigDecimal(number, DEFAULT_MATH_CONTEXT));
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
								DEFAULT_MATH_CONTEXT))
				+
				"\u2031";
	}

	/**
	 * Converts to {@link BigDecimal}, if necessary, or casts, if possible.
	 * 
	 * @param number
	 *            The {@link Number}
	 * @param mathContext
	 *            the {@link MathContext}
	 * @return the {@code number} as {@link BigDecimal}
	 */
	private static final BigDecimal getBigDecimal(Number number,
			MathContext mathContext) {
		if (number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else {
			return new BigDecimal(number.doubleValue(), mathContext);
		}
	}

	/**
	 * Calculate a BigDecimal value for a Permil e.g. "3" (3 permil) will
	 * generate .003
	 * 
	 * @return java.math.BigDecimal
	 * @param decimal
	 *            java.math.BigDecimal
	 */
	private static final BigDecimal calcBasisPoint(BigDecimal decimal) {
		return decimal.divide(ONE_TENTHOUSAND, DEFAULT_MATH_CONTEXT); // we now
																		// have
																		// .003
	}

	@Override
	public ComplexType getInputType() {
		return INPUT_TYPE;
	}

	@Override
	public Class<MonetaryAmount> getResultType() {
		return MonetaryAmount.class;
	}

	@Override
	public MonetaryAmount calculate(ComplexValue<ComplexType> input) {
		return Money.from(input.get("amount", MonetaryAmount.class)).multiply(input.get("basisPoints", Number.class));
	}

}
