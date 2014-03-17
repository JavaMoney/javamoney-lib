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
package org.javamoney.calc.function;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

/**
 * This singleton class provides access to the predefined monetary functions.
 * <p>
 * The class is thread-safe, which is also true for all functions returned by
 * this class.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryCalculations {
	/** default Math context used. */
	private static final MathContext DEFAULT_MATH_CONTEXT = initDefaultMathContext();

	/**
	 * The shared instance of this class.
	 */
	private static final Total TOTAL = new Total();
	/** Shared average instance. */
	private static final AverageMean AVERAGE = new AverageMean();

	/**
	 * The shared instance of this class.
	 */
	private static final Maximum MAXIMUM = new Maximum();
	/** Shared minimum instance. */
	private static final Minimum MINIMUM = new Minimum();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryCalculations() {
		// Singleton constructor
	}

	public static MonetaryPredicate<MonetaryAmount> withFlavors(String... flavors) {
		return new MonetaryAmountFlavorPredicate<>(flavors);
	}

	@SafeVarargs
	public static MonetaryPredicate<MonetaryAmount> withFlavors(
			Iterable<String>... flavors) {
		return new MonetaryAmountFlavorPredicate<>(flavors);
	}

	/**
	 * Get {@link MathContext} for {@link org.javamoney.moneta.function.Permil} instances.
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
	 * Factory method creating a new instance with the given {@code Number) percent value;
	 * @param decimal the decimal value of the percent operator being created.
	 * 
	 * @return a new  {@code Percent} operator
	 */
	public static MonetaryOperator percent(Number number) {
		return percent(getBigDecimal(number, DEFAULT_MATH_CONTEXT));
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryCalculation<Iterable<? extends MonetaryAmount>> total() {
		return TOTAL;
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryCalculation<Iterable<? extends MonetaryAmount>> average() {
		return AVERAGE;
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryCalculation<Iterable<? extends MonetaryAmount>> minimum() {
		return MINIMUM;
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryCalculation<Iterable<? extends MonetaryAmount>> maximum() {
		return MAXIMUM;
	}

}
