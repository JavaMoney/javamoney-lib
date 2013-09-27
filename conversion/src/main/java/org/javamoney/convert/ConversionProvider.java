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
package org.javamoney.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAdjuster;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the {@link MonetaryConversions} singleton. Hereby a
 * instance of this class must only provide conversion data for exact one
 * exchange rate type, defined by {@link #getExchangeRateType()}.
 * <p>
 * Implementations of this interface are required to be thread save.
 * <p>
 * Implementations of this class must neither be immutable nor serializable.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access the {@link ExchangeRateType} for this {@link ConversionProvider}.
	 * Each instance of {@link ConversionProvider} provides conversion data for
	 * exact one exchange rate type.
	 * 
	 * @return the exchange rate type, never {@code null}.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * available from this provider. This method should check, if a given rate
	 * is <i>currently</i> defined. It should be the same as
	 * {@code isAvailable(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @return {@code true}, if such an {@link ExchangeRate} is currently
	 *         defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * defined.
	 * <p>
	 * Note that the UTC timestamp models the instance, when the queried rate
	 * must have been valid. The rate's validity may be
	 * <ul>
	 * <li>Completely undefined (
	 * {@code validFromMillis == null && validToMillis == null}</li>
	 * <li>May have {@code validFromMillis <= timestamp}.</li>
	 * <li>May have {@code validToMillis >= timestamp}.</li>
	 * <li>Or both of the two above.</li>
	 * </ul>
	 * 
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @param timestamp
	 *            the UTC timestamp when the {@link ExchangeRate} queried was
	 *            valid.
	 * @return {@code true}, if such an {@link ExchangeRate} is currently
	 *         defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			long timestamp);

	/**
	 * Get an {@link ConversionRate} for a given timestamp (including historic
	 * rates).
	 * <p>
	 * Note that the UTC timestamp models the instance, when the queried rate
	 * must have been valid. The rate's validity may be
	 * <ul>
	 * <li>Completely undefined (
	 * {@code validFromMillis == null && validToMillis == null}</li>
	 * <li>May have {@code validFromMillis <= timestamp}.</li>
	 * <li>May have {@code validToMillis >= timestamp}.</li>
	 * <li>Or both of the two above.</li>
	 * </ul>
	 * 
	 * @param base
	 *            The base {@link CurrencyUnit}
	 * @param term
	 *            The term {@link CurrencyUnit}
	 * @param timestamp
	 *            the target timestamp for which the {@link ExchangeRate} is
	 *            queried.
	 * @return the matching {@link ExchangeRate}.
	 * @throws CurrencyConversionException
	 *             If no such rate is available.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, long timestamp);

	/**
	 * Access a {@link ExchangeRate} using the given currencies. The
	 * {@link ExchangeRate} may be, depending on the data provider, eal-time or
	 * deferred. This method should return the rate that is <i>currently</i>
	 * valid. It should be the same as
	 * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            base {@link CurrencyUnit}.
	 * @param term
	 *            term {@link CurrencyUnit}.
	 * @return the matching {@link ExchangeRate}.
	 * @throws CurrencyConversionException
	 *             If no such rate is available.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term);

	/**
	 * The method reverses the {@link ExchangeRate} to a rate mapping from term
	 * to base {@link CurrencyUnit}. Hereby the factor must <b>not</b> be
	 * recalculated as {@code 1/oldFactor}, since typically reverse rates are
	 * not symmetric in most cases.
	 * 
	 * @return the matching reversed {@link ExchangeRate}, or {@code null}, if
	 *         the rate cannot be reversed.
	 */
	public ExchangeRate getReversed(ExchangeRate rate);

	/**
	 * Access a {@link CurrencyConverter} that can be applied as a
	 * {@link MonetaryAdjuster} to an amount.
	 * 
	 * @return a new instance of a corresponding {@link CurrencyConverter},
	 *         never {@code null}.
	 */
	public CurrencyConverter getConverter();

}
