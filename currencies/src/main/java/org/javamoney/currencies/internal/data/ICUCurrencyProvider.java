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
import javax.money.CurrencyContext;
import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a {@link CurrencyProviderSpi} that provides the additional
 * currencies available from the ICO library, but not part of the JDK.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class ICUCurrencyProvider implements CurrencyProviderSpi{

    private static final Logger LOGGER = LoggerFactory.getLogger(ICUCurrencyProvider.class);

    private static final CurrencyContext CURRENCY_CONTEXT = CurrencyContextBuilder.of("ICU").build();

    private Map<String,CurrencyUnit> currencies = new ConcurrentHashMap<>();

    public ICUCurrencyProvider(){
        for(com.ibm.icu.util.Currency currency : com.ibm.icu.util.Currency.getAvailableCurrencies()){
            ICUCurrency icuInstance = new ICUCurrency(currency);
            this.currencies.put(icuInstance.getCurrencyCode(), icuInstance);
        }
    }

    @Override
    public String getProviderName(){
        return "ICU";
    }

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query){
        if(query.getTimestamp() != null){
            return Collections.emptySet();
        }
        Set<CurrencyUnit> currencies = new HashSet<>();
        if(!query.getCurrencyCodes().isEmpty()){
            for(String code : query.getCurrencyCodes()){
                CurrencyUnit cu = this.currencies.get(code);
                if(cu != null){
                    currencies.add(cu);
                }
            }
        }else{
            currencies.addAll(this.currencies.values());
        }
        return currencies;
    }


    private final class ICUCurrency implements CurrencyUnit{


        private com.ibm.icu.util.Currency currency;

        public ICUCurrency(com.ibm.icu.util.Currency currency){
            this.currency = currency;
        }

        public String getCurrencyCode(){
            return this.currency.getCurrencyCode();
        }

        public int getNumericCode(){
            return this.currency.getNumericCode();
        }

        public int getDefaultFractionDigits(){
            return this.currency.getDefaultFractionDigits();
        }

        @Override
        public CurrencyContext getCurrencyContext(){
            return CURRENCY_CONTEXT;
        }

        @Override
        public String toString(){
            return this.currency.toString();
        }

        public String getDisplayName(Locale locale){
            return this.currency.getDisplayName(locale);
        }

        @Override
        public int compareTo(CurrencyUnit o){
            return this.getCurrencyCode().compareTo(o.getCurrencyCode());
        }
    }

}
