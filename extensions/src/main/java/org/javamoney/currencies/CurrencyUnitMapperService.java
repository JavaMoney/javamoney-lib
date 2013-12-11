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

import javax.money.CurrencyUnit;
import javax.money.bootstrap.Bootstrap;

import org.javamoney.currencies.spi.CurrencyUnitMapperSpi;
import org.javamoney.currencies.spi.MonetaryCurrenciesSingletonSpi;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link MonetaryCurrenciesSingletonSpi} implementation. It
 * is extended for different runtime scenarios, hereby allowing the spi
 * implementation loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class CurrencyUnitMapperService {

	public CurrencyUnit map(String targetNamespace, CurrencyUnit unit) {
		for (CurrencyUnitMapperSpi prov : Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitMapperSpi.class)) {
			CurrencyUnit mappedUnit = prov.map(unit, targetNamespace, null);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
			Long timestamp) {
		if (timestamp == null) {
			return map(targetNamespace, currencyUnit);
		}
		for (CurrencyUnitMapperSpi prov : Bootstrap.getServiceProvider()
				.getServices(CurrencyUnitMapperSpi.class)) {
			CurrencyUnit mappedUnit = prov.map(currencyUnit, targetNamespace,
					timestamp);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

}
