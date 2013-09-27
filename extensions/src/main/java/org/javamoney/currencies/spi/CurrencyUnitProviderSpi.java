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
package org.javamoney.currencies.spi;

import java.util.Collection;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides accessory
 * for {@link CurrencyUnit}.<br/>
 * It is the responsibility of the registered
 * {@link MonetaryCurrenciesSingletonSpi} to load the and manage the instances
 * of {@link CurrencyUnitProviderSpi}. Depending on the runtime environment,
 * implementations may be loaded using the {@link ServiceLoader}. But also
 * alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderSpi {

	/**
	 * Access the namespace this provider defines. An instance of
	 * {@link CurrencyUnitProviderSpi} alwyas provides {@link CurrencyUnit} for
	 * exact one namespace. Nevertheless multiple implementations of
	 * {@link CurrencyUnitProviderSpi} may serve currencies for the same
	 * namespace, if they do not conflict related to their currency code or
	 * numericCode (if defined).
	 * 
	 * @return the namespace of this provider, never {@code null}.
	 */
	public String getNamespace();

	/**
	 * Access all {@link CurrencyUnit} instances.
	 * 
	 * @return the {@link CurrencyUnit} instances known to this provider
	 *         instance, never {@code null}. If the provider can not provide a
	 *         full list of all currencies an empoty {@link Collection} should
	 *         be returned.
	 */
	public Collection<CurrencyUnit> getAll();

	/**
	 * Access a {@link CurrencyUnit} by code.
	 * <p>
	 * Note that the SPI is defined to return {@code null} instead of throwing a
	 * {@link UnknownCurrencyException}, since several SPI implementation may
	 * serve a namespace at the same time.
	 * 
	 * @param code
	 *            the {@link CurrencyUnit} found, or {@code null}.
	 * @return the {@link CurrencyUnit} found, or {@code null}.
	 */
	public CurrencyUnit get(String code);

	/**
	 * Checks if a currency is defined using its code.
	 * 
	 * @param code
	 *            The code that, together with the namespace of this provider
	 *            identifies the currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public boolean isAvailable(String code);
}
