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

import org.javamoney.convert.spi.MonetaryConversionsSingletonSpi;

/**
 * This interface defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ConversionProvider}, a term {@link CurrencyUnit} and
 * (optionally) a target timestamp.<br/>
 * This interface serves a an API for the clients, but also must be implemented
 * and registered as SPI to the mechanisms required by the
 * {@link MonetaryConversionsSingletonSpi} implementation.
 * <p>
 * Instances of this class are required to be thread-safe, but it is not a
 * requirement that they are serializable. In a EE context they can be
 * implemented using contextual beans.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConversion extends MonetaryAdjuster {

	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never {@code null}.
	 */
	public CurrencyUnit getTermCurrency();

	/**
	 * Access the target timestamp of this conversion instance.
	 * 
	 * @return the target timestamp , or {@code null} for latest rates.
	 */
	public Long getTargetTimestamp();

	/**
	 * Get the {@link ExchangeRateType} of this conversion instance.
	 * 
	 * @return the exchange rate type of this conversion instance, never
	 *         {@code null}.
	 */
	public ExchangeRateType getRateType();

}
