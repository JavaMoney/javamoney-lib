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
import javax.money.MonetaryAmount;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by a {@link ConversionProvider} implementation.
 * <p>
 * Instances fo this interface must be thread-safe, however they are not
 * required to be serializable.
 * 
 * @see ConversionProvider
 * @see MonetaryConversions
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConverter {

	/**
	 * Get the {@link ExchangeRateType} that this provider instance is providing
	 * data for.
	 * 
	 * @return the exchange rate type if this instance.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Method that converts the source amount to an {@link MonetaryAmount} in
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount to be converted.
	 * @param targetCurrency
	 *            The target currency
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public <T extends MonetaryAmount> T convert(T amount,
			CurrencyUnit targetCurrency);

	/**
	 * Method that converts the source amount to an {@link MonetaryAmount} in
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount.
	 * @param term
	 *            The term (target) currency
	 * @param timestamp
	 *            the target time stamp for which the exchange rate is queried,
	 *            or {@code null}, for acquiring a current rate.
	 * @return the converted {@code MonetaryAmount}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public <T extends MonetaryAmount> T convert(T amount,
			CurrencyUnit term, Long timestamp);

	/**
	 * Access a {@link CurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see CurrencyConversion
	 * @see FixedCurrencyConversion
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see CurrencyConversion
	 * @see FixedCurrencyConversion
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @param targetTimestamp
	 *            for which the conversion is targeted.
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term, long targetTimestamp);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method. The rate
	 * required for conversion then is accessed lazily from the corresponding
	 * (owning) {@link ConversionProvider}.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see ConversionProvider
	 * @param term
	 *            The terminating currency
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method. The rate
	 * required for conversion then is accessed lazily from the corresponding
	 * (owning) {@link ConversionProvider}.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @param term
	 *            The terminating currency
	 * @param targetTimestamp
	 *            for which the conversion is targeted.
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term,
			long targetTimestamp);

}
