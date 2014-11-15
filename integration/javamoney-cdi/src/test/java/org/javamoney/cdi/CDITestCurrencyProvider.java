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
