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

import org.javamoney.currencies.spi.CurrencyMappingsSingletonSpi;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;
import javax.money.spi.Bootstrap;
import java.util.Set;

/**
 * This is the service component for mapping of {@link CurrencyUnit} to grtoups
 * (aka namespaces) and mapping between different {@link CurrencyUnit} instances
 * and/or namespaces.
 * <p>
 * This class is thread-safe. However it delegates all calls to the registered
 * {@link CurrencyMappingsSingletonSpi} SPI.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class CurrencyMappings {

    private static final CurrencyMappingsSingletonSpi CURRENCIES_SPI =
            Bootstrap.getService(CurrencyMappingsSingletonSpi.class);

    /**
     * Singleton constructor.
     */
    private CurrencyMappings() {
    }

    /**
     * This method allows to evaluate, if the given currency namespace is
     * defined. "ISO-4217" should be defined in all environments (default).
     *
     * @param namespace the required namespace
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
    public static Set<String> getNamespaces() {
        return CURRENCIES_SPI.getNamespaces();
    }

    /**
     * Evaluates the available currency namespaces of a currency code.
     *
     * @param currencyCode The currencyCode that identifies the {@link CurrencyUnit}
     *                     instance, not null.
     * @return the {@link Set} of namespaces that {@link CurrencyUnit} is a
     * member of.
     */
    public static Set<String> getNamespaces(String currencyCode) {
        return CURRENCIES_SPI.getNamespaces(currencyCode);
    }

    /**
     * Evaluates the available currency namespaces of a currency unit.
     *
     * @param currencyUnit The {@link CurrencyUnit} instance, not null.
     * @return the {@link Set} of namespaces that {@link CurrencyUnit} is a
     * member of.
     */
    public static Set<String> getNamespaces(CurrencyUnit currencyUnit) {
        return CURRENCIES_SPI.getNamespaces(currencyUnit.getCurrencyCode());
    }

    /**
     * Access a {@link javax.money.CurrencyQuery} for accessing all currencies of a given namespace.
     *
     * @param namespace The target namespace, not {@code null}.
     * @return a corresponding {@link javax.money.CurrencyQuery}, never null.
     * @throws UnknownCurrencyException if the required namespace is not defined.
     * @see #getNamespaces()
     * @see javax.money.MonetaryCurrencies#getCurrencies(javax.money.CurrencyQuery)
     */
    public static CurrencyQuery getNamespaceQuery(String namespace) {
        return CURRENCIES_SPI.getNamespaceQuery(namespace);
    }

    /**
     * Access a {@link javax.money.CurrencyQuery} for mapping the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param currencyUnit    The source unit, never {@code null}.
     * @param targetNamespace the target namespace, never {@code null}.
     * @return a corresponding {@link javax.money.CurrencyQuery}, never null.
     * @see javax.money.MonetaryCurrencies#getCurrencies(javax.money.CurrencyQuery)
     */
    public static CurrencyQuery getMappingQuery(CurrencyUnit currencyUnit,
                                                String targetNamespace) {
        return CURRENCIES_SPI.getMappingQuery(currencyUnit, targetNamespace);
    }

}
