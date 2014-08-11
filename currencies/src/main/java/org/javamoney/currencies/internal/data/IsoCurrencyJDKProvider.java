/*
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil.
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
package org.javamoney.currencies.internal.data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import javax.money.spi.CurrencyProviderSpi;

import org.javamoney.currencies.spi.CurrencyUnitNamespaceProviderSpi;

/**
 * Basic implementation of a {@link CurrencyUnitNamespaceProviderSpi} that provides the
 * ISO 4217 currencies available from the JDK {@link Currency} class.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class IsoCurrencyJDKProvider implements CurrencyProviderSpi {

	private Map<String, CurrencyUnit> currencies;

	private void init(){
		if(currencies==null){
			currencies = new ConcurrentHashMap<String, CurrencyUnit>();
			Set<Currency> jdkCurrencies = Currency.getAvailableCurrencies();
			for (Currency jdkCurrency : jdkCurrencies) {
				CurrencyUnit currency = MonetaryCurrencies.getCurrency(jdkCurrency
						.getCurrencyCode());
				this.currencies.put(currency.getCurrencyCode(), currency);
			}
		}
	}

	public CurrencyUnit getCurrencyUnit(String currencyCode) {
		init();
		return this.currencies.get(currencyCode);
	}

	public CurrencyUnit getCurrencyUnit(Locale locale) {
		try{
			return this.currencies.get(Currency.getInstance(locale).getCurrencyCode());
		}
		catch(Exception e){
			return null;
		}
	}

	public Collection<CurrencyUnit> getCurrencies() {
		return currencies.values();
	}

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        Set<CurrencyUnit> result = new HashSet<>();
        for(String code:query.getCurrencyCodes()){
            CurrencyUnit u = this.currencies.get(code);
            if(u!=null){
                result.add(u);
            }
            else{
                throw new IllegalArgumentException("No such currency: " + code);
            }
        }
        return result;
    }
}
