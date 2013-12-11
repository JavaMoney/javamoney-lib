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
package org.javamoney.currencies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import javax.money.bootstrap.Bootstrap;

import org.javamoney.currencies.spi.MonetaryCurrenciesSingletonSpi;

import com.ibm.icu.util.Currency;

/**
 * This is the service component for accessing Java Money Currencies, evaluating
 * currency namespaces, access historic currencies and map between currencies.
 * <p>
 * This class is thread-safe. However it delegates all calls to the registered
 * {@link MonetaryCurrenciesSingletonSpi} SPI (using the {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9.2
 */
public final class MonetaryCurrencyMapper {

	private static final MonetaryCurrenciesSingletonSpi CURRENCIES_SPI = Bootstrap.getServiceProvider()
			.getService(MonetaryCurrenciesSingletonSpi.class, new DefaultMonetaryCurrenciesSpi());

	/**
	 * Singleton constructor.
	 */
	private MonetaryCurrencyMapper() {
	}


	/**
	 * This method allows to evaluate, if the given currency namespace is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required namespace
	 * @return {@code true}, if the namespace exists.
	 */
	public static boolean isNamespaceAvailable(String namespace) {
		return CURRENCIES_SPI.isNamespaceAvailable(namespace);
	}

	/**
	 * This method allows to access all namespaces currently defined. "ISO-4217"
	 * should be defined in all environments (default).
	 * 
	 * @return the array of currently defined namespaces.
	 */
	public static Collection<String> getNamespaces() {
		return CURRENCIES_SPI.getNamespaces();
	}


	/**
	 * Evaluates the currency namespace of a currency code.
	 * 
	 * @param namespace
	 *            The currency namespace, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public static Collection<String> getNamespaces(String code) {
		return CURRENCIES_SPI.getNamespaces(code);
	}
	
	/**
	 * Evaluates the currency namespace of a currency code.
	 * 
	 * @param namespace
	 *            The currency namespace, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public static Collection<String> getNamespaces(CurrencyUnit currencyUnit) {
		return CURRENCIES_SPI.getNamespaces(currencyUnit);
	}

	/**
	 * Access all currencies for a given namespace.
	 * 
	 * @see #getNamespaces()
	 * @see #getDefaultNamespace()
	 * @param namespace
	 *            The target namespace, not {@code null}.
	 * @return The currencies found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required namespace is not defined.
	 */
	public static Collection<CurrencyUnit> getCurrencies(String namespace) {
		return CURRENCIES_SPI.getCurrencies(namespace);
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public static CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace) {
		return CURRENCIES_SPI.map(currencyUnit, targetNamespace);
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @param timestamp
	 *            the target timestamp
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public static CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace, long timestamp) {
		return CURRENCIES_SPI.map(currencyUnit, targetNamespace, timestamp);
	}

	/**
	 * Default implementation of {@link MonetaryCurrenciesSingletonSpi}, active
	 * if no instance of {@link MonetaryCurrenciesSingletonSpi} was registered
	 * using the {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultMonetaryCurrenciesSpi implements
			MonetaryCurrenciesSingletonSpi {
		/** Error message for unsupported operations. */
		private static final String ERROR_MESSAGE = "No "
				+ MonetaryCurrenciesSingletonSpi.class.getName()
				+ " registered.";
		private static final Set<String> ISO_NS_COLLECTION = new HashSet<String>();

		static{
			ISO_NS_COLLECTION.add("ISO-4217");
		}
		
		/**
		 * This method allows to evaluate, if the given currency namespace is
		 * defined. {@code "ISO-4217"} should be defined in all environments
		 * (default).
		 * 
		 * @param namespace
		 *            the required namespace
		 * @return {@code true}, if the namespace exists.
		 */
		@Override
		public boolean isNamespaceAvailable(String namespace) {
			return ISO_NS_COLLECTION.contains(namespace);
		}

		/**
		 * This method allows to access all namespaces currently defined.
		 * "ISO-4217" should be defined in all environments (default).
		 * 
		 * @return the collection of currently defined namespace, never
		 *         {@code null}.
		 */
		@Override
		public Collection<String> getNamespaces() {
			return ISO_NS_COLLECTION;
		}

		/**
		 * This method maps the given {@link CurrencyUnit} to another
		 * {@link CurrencyUnit} with the given target namespace.
		 * 
		 * @param unit
		 *            The source unit, never {@code null}.
		 * @param currencyCode
		 *            the target currencyCode, never {@code null}.
		 * @return The mapped {@link CurrencyUnit}, or {@code null}.
		 */
		@Override
		public CurrencyUnit map(CurrencyUnit currencyUnit,
				String currencyCode) {
			return null;
		}

		/**
		 * This method maps the given {@link CurrencyUnit} to another
		 * {@link CurrencyUnit} with the given target namespace.
		 * 
		 * @param unit
		 *            The source unit, never {@code null}.
		 * @param targetNamespace
		 *            the target namespace, never {@code null}.
		 * @return The mapped {@link CurrencyUnit}, or {@code null}.
		 */
		@Override
		public CurrencyUnit map(CurrencyUnit currencyUnit,
				String currencyCode, long timestamp) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getAll(java.lang
		 * .String)
		 */
		@Override
		public Collection<CurrencyUnit> getCurrencies(String namespace) {
			List<CurrencyUnit> result = new ArrayList<>();
			for(Currency currency:Currency.getAvailableCurrencies()){
				result.add(MonetaryCurrencies.getCurrency(currency.getCurrencyCode()));
			}
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getNamespace(java
		 * .lang.String)
		 */
		@Override
		public Set<String> getNamespaces(String code) {
			if(Currency.isAvailable(code, null, null)){
				return ISO_NS_COLLECTION;
			}
			return Collections.emptySet();
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getNamespace(java
		 * .lang.String)
		 */
		@Override
		public Set<String> getNamespaces(CurrencyUnit code) {
			if(Currency.isAvailable(code.getCurrencyCode(), null, null)){
				return ISO_NS_COLLECTION;
			}
			return Collections.emptySet();
		}

	}
}
