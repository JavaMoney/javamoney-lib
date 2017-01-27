/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *
 *
 * Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.cdi;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.inject.Singleton;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Test provider that returns exactly one currency. The provider is only loaded with CDI and not registered
 * as a ServiceLoader service.
 * Created by Anatole on 15.11.2014.
 */
@Singleton
public class CDITestCurrencyProvider implements CurrencyProviderSpi {

    private Set<CurrencyUnit> currencies = new HashSet<>();

    public CDITestCurrencyProvider() {
        currencies.add(CurrencyUnitBuilder.of("CDITest", "CDITestCurrencyProvider").build());
        currencies = Collections.unmodifiableSet(currencies);
    }

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        if (query.getCurrencyCodes().contains("CDITest")) {
            return currencies;
        }
        return Collections.emptySet();
    }
}
