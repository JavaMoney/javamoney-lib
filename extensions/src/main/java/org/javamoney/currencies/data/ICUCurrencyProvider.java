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
package org.javamoney.currencies.data;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;

import org.javamoney.util.Displayable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Implements a {@link CurrencyUnitProviderSpi} that provides the additional
 * currencies available from the ICO library, but not part of the JDK.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class ICUCurrencyProvider implements CurrencyProviderSpi {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ICUCurrencyProvider.class);

	private Map<String, CurrencyUnit> currencies = new ConcurrentHashMap<String, CurrencyUnit>();

	public ICUCurrencyProvider() {
		for(com.ibm.icu.util.Currency currency:com.ibm.icu.util.Currency.getAvailableCurrencies()){
			ICUCurrency icuInstance = new ICUCurrency(currency);
			this.currencies.put(icuInstance.getCurrencyCode(), icuInstance);
		}
	}

	@Override
	public CurrencyUnit getCurrencyUnit(String currencyCode) {
		return this.currencies.get(currencyCode);
	}

	@Override
	public CurrencyUnit getCurrencyUnit(Locale locale) {
		return null;
	}

	
	private final class ICUCurrency implements CurrencyUnit, Displayable {
		private com.ibm.icu.util.Currency currency;

		public ICUCurrency(com.ibm.icu.util.Currency currency) {
			this.currency = currency;
		}
		
		public String getCurrencyCode() {
			return this.currency.getCurrencyCode();
		}

		public int getNumericCode() {
			return this.currency.getNumericCode();
		}

		public int getDefaultFractionDigits() {
			return this.currency.getDefaultFractionDigits();
		}

		@Override
		public String toString() {
			return this.currency.toString();
		}

		public String getDisplayName(Locale locale) {
			return this.currency.getDisplayName(locale);
		}

	}

}
