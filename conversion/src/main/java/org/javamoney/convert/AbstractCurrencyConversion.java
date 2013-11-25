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

import java.math.BigDecimal;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * Abstract base class used for implementing currency conversion.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractCurrencyConversion implements CurrencyConversion {

	/**
	 * Get the exchange rate type that this {@link MonetaryOperator} instance is
	 * using for conversion.
	 * 
	 * @see #apply(MonetaryAmount)
	 * @return the {@link ExchangeRate} to be used, or null, if this conversion
	 *         is not supported (will lead to a
	 *         {@link CurrencyConversionException}.
	 */
	protected abstract ExchangeRate getExchangeRate(MonetaryAmount amount);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} based on the {@link ExchangeRate} of this
	 * conversion.<br/>
	 * 
	 * @see #getExchangeRate(MonetaryAmount)
	 * @param amount
	 *            The source amount
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount apply(MonetaryAmount amount) {
		ExchangeRate rate = getExchangeRate(amount);
		if (rate == null || !amount.getCurrency().equals(rate.getBase())) {
			throw new CurrencyConversionException(amount.getCurrency(),
					rate == null ? null : rate.getTerm(), null);
		}
		return Money.of(rate.getTerm(), Money.from(amount).multiply(rate.getFactor())
				.asType(BigDecimal.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [MonetaryAmount -> MonetaryAmount"
				+ "]";
	}

}
