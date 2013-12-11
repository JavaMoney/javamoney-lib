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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.bootstrap.Bootstrap;

import org.javamoney.currencies.spi.CurrencyUnitNamespaceSpi;
import org.javamoney.currencies.spi.MonetaryCurrenciesSingletonSpi;
import org.javamoney.regions.RegionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link MonetaryCurrenciesSingletonSpi} implementation. It
 * is extended for different runtime scenarios, hereby allowing the spi
 * implementation loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class CurrencyUnitProviderService {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	public Collection<CurrencyUnit> getCurrencies(String namespace) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		Collection<CurrencyUnitNamespaceSpi> provList = Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitNamespaceSpi.class);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitNamespaceSpi prov : provList) {
			Collection<CurrencyUnit> currencies = prov.getCurrencies(namespace);
			if (currencies != null) {
				result.addAll(currencies);
			}
		}
		return result;
	}

	/**
	 * Access all defined currency namespaces.
	 * 
	 * @return the namespaces found, never null.
	 */
	public Set<String> getNamespaces() {
		Set<String> result = new HashSet<String>();
		Collection<CurrencyUnitNamespaceSpi> provList = Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitNamespaceSpi.class);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitNamespaceSpi prov : provList) {
			result.addAll(prov.getNamespaces());
		}
		return result;
	}

	public boolean isNamespaceAvailable(String namespace) {
		Collection<CurrencyUnitNamespaceSpi> provList = Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitNamespaceSpi.class);
		if (provList == null) {
			return false;
		}
		for (CurrencyUnitNamespaceSpi prov : provList) {
			if (prov.isNamespaceAvailable(namespace)) {
				return true;
			}
		}
		return false;
	}

	public Set<String> getNamespaces(String code) {
		Set<String> result = new HashSet<>();
		Collection<CurrencyUnitNamespaceSpi> provList = Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitNamespaceSpi.class);
		if (provList == null) {
			return result;
		}
		for (CurrencyUnitNamespaceSpi prov : provList) {
			result.addAll(prov.getNamespaces(code));
		}
		return result;
	}

}
