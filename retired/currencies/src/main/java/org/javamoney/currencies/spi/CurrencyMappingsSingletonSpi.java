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

import java.util.Set;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;

/**
 * This interface must be implemented and registered using the
 * {@code ServiceLoader}. It implements the functionality provided by the
 * {@code MonetaryCurrencies} singleton and is responsible for loading and
 * managing of {@link CurrencyUnitNamespaceProviderSpi} and {@link CurrencyUnitMapperSpi}
 * instances and delegating according calls to the appropriate providers.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 *
 * @author Anatole Tresch
 */
public interface CurrencyMappingsSingletonSpi {

    /**
     * This method allows to evaluate, if the given currency namespace is
     * defined. {@code "ISO-4217"} should be defined in all environments
     * (default).
     *
     * @param namespace the required namespace
     * @return {@code true}, if the namespace exists.
     */
    public boolean isNamespaceAvailable(String namespace);

    /**
     * This method allows to access all namespaces currently defined.
     * {@code "ISO-4217"} should be defined in all environments (default).
     *
     * @return the array of currently defined namespace.
     */
    public Set<String> getNamespaces();

    /**
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param currencyUnit    The source {@link CurrencyUnit}, never {@code null}.
     * @param targetNamespace the target namespace, never {@code null}.
     * @return The mapped {@link CurrencyUnit}, or {@code null}.
     */
    public CurrencyQuery getMappingQuery(CurrencyUnit currencyUnit,
                                         String targetNamespace);

    /**
     * Access all currencies for a given namespace.
     *
     * @param namespace The target namespace, not {@code null}.
     * @return The currencies found, never {@code null}.
     * @throws javax.money.UnknownCurrencyException if the required namespace is not defined.
     * @see #getNamespaces()
     */
    public CurrencyQuery getNamespaceQuery(String namespace);

    /**
     * Evaluates the currency namespace of a currency code.
     *
     * @param code The currency code.
     * @return {@code true}, if the currency is defined.
     */
    public Set<String> getNamespaces(String code);

    /**
     * Evaluates the currency namespace of a currency code.
     *
     * @param currency The currency, not null.
     * @return {@code true}, if the currency is defined.
     */
    public Set<String> getNamespaces(CurrencyUnit currency);

}
