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

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConversion;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;

/**
 * This class defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ConversionProvider}, a term {@link CurrencyUnit} and a
 * target timestamp.
 * 
 * @author Anatole Tresch
 */
public class LazyBoundCurrencyConversion extends AbstractCurrencyConversion
		implements CurrencyConversion {

	private ConversionProvider rateProvider;
	private CurrencyUnit termCurrency;
	private Long targetTimestamp;

	public LazyBoundCurrencyConversion(CurrencyUnit termCurrency,
			ConversionProvider rateProvider) {
		if (rateProvider == null) {
			throw new IllegalArgumentException("ExchangeRateProvider required.");
		}
		this.rateProvider = rateProvider;
		if (termCurrency == null) {
			throw new IllegalArgumentException("Term CurrencyUnit required.");
		}
		this.termCurrency = termCurrency;
	}

	public LazyBoundCurrencyConversion(CurrencyUnit termCurrency,
			ConversionProvider rateProvider, long targetTimestamp) {
		this(termCurrency, rateProvider);
		this.targetTimestamp = targetTimestamp;
	}

	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never null.
	 */
	public CurrencyUnit getTermCurrency() {
		return this.termCurrency;
	}

	/**
	 * Access the target timestamp of this conversion instance.
	 * 
	 * @return the target timestamp , or null for latest rates.
	 */
	public Long getTargetTimestamp() {
		return this.targetTimestamp;
	}

	/**
	 * Get the {@link ExchangeRateType} of this conversion instance.
	 * 
	 * @return the exchange rate type of this conversion instance, never null.
	 */
	public ExchangeRateType getRateType() {
		return this.rateProvider.getExchangeRateType();
	}

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the exchange rate type if this instance.
	 */
	protected ExchangeRate getExchangeRate(MonetaryAmount amount) {
		return this.rateProvider.getExchangeRate(amount.getCurrency(),
				termCurrency);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyConversion [MonetaryAmount -> MonetaryAmount; rateType="
				+ rateProvider.getExchangeRateType()
				+ ", termCurrency="
				+ termCurrency + ", targetTimestamp=" + targetTimestamp + "]";
	}

}
