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

import java.util.Arrays;
import java.util.Collection;

import javax.money.CurrencyUnit;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConverter;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.spi.MonetaryConversionsSingletonSpi;

public class TestMonetaryConversionSpi implements MonetaryConversionsSingletonSpi {

	private ConversionProvider dummyProvider = new DummyConversionProvider();
	
	@Override
	public ConversionProvider getConversionProvider(ExchangeRateType type) {
		if(ExchangeRateType.of("test").equals(type)){
			return dummyProvider;
		}
		return null;
	}

	@Override
	public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		return Arrays.asList(new ExchangeRateType[] { ExchangeRateType
				.of("test") });
	}

	@Override
	public boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return ExchangeRateType.of("test").equals(type);
	}

	public static final class DummyConversionProvider implements ConversionProvider{

		@Override
		public ExchangeRateType getExchangeRateType() {
			return ExchangeRateType.of("test");
		}

		@Override
		public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
			return false;
		}

		@Override
		public boolean isAvailable(CurrencyUnit CurrencyUnit,
				CurrencyUnit target, long timestamp) {
			return false;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
				CurrencyUnit targetCurrency, long timestamp) {
			return null;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit source,
				CurrencyUnit target) {
			return null;
		}

		@Override
		public ExchangeRate getReversed(ExchangeRate rate) {
			return null;
		}

		@Override
		public CurrencyConverter getConverter() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
