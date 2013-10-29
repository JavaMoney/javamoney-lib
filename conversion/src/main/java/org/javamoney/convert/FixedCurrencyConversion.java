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
import java.math.MathContext;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public class FixedCurrencyConversion extends AbstractCurrencyConversion {

	private ExchangeRate rate;

	public FixedCurrencyConversion(ExchangeRate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("Rate required.");
		}
		this.rate = rate;
	}

	@Override
	public CurrencyUnit getTermCurrency() {
		return this.rate.getTerm();
	}

	@Override
	public Long getTargetTimestamp() {
		return this.rate.getValidFromTimestamp();
	}

	@Override
	public ExchangeRateType getRateType() {
		return this.rate.getExchangeRateType();
	}

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	protected ExchangeRate getExchangeRate(MonetaryAmount amount) {
		if (!amount.getCurrency().equals(this.rate.getBase())) {
			throw new CurrencyConversionException(amount.getCurrency(),
					rate.getTerm(), null);
		}
		return this.rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FixedCurrencyConversion [MonetaryAmount -> MonetaryAmount; rate="
				+ rate + "]";
	}

	@Override
	public MonetaryAmount adjustInto(MonetaryAmount amount) {
		ExchangeRate rate = getExchangeRate(amount);
		Money money = Money.from(amount);
		return Money.of(rate.getTerm(), money.asType(BigDecimal.class)
				.multiply(rate.getFactor(), MathContext.DECIMAL64));
	}
}
