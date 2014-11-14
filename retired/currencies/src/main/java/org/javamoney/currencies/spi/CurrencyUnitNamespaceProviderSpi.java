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
package org.javamoney.currencies.spi;

import java.util.Collection;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.spi.Bootstrap;

/**
 * This class models namespaces that allow to group currencies. Examples for
 * namespaces for different organization units, countries or technical systems.<br>
 * It is the responsibility of the registered {@link Bootstrap} to load the and
 * manage the instances of {@link CurrencyUnitNamespaceProviderSpi}.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyUnitNamespaceProviderSpi {

    /**
     * Access the namespaces this provider defines. An instance of
     * {@link CurrencyUnitNamespaceProviderSpi} may define multiple namespaces containing
     * {@link CurrencyUnit}. Nevertheless multiple implementations of
     * Additionally a {@link CurrencyUnit} may be part of multiple namespaces,
     * if they do not conflict related to their currency code or numericCode (if
     * defined).
     *
     * @return the namespaces that this provider defines, never {@code null}.
     */
    public Collection<String> getNamespaces();

    /**
     * Access all {@link CurrencyUnit} instances.
     *
     * @param namespace The target namespace, never {@code null}.
     * @return the {@link CurrencyUnit} instances known to this provider
     * instance, never {@code null}. If the provider can not provide a
     * full list of all currencies an empty {@link Collection} should be
     * returned.
     */
    public Collection<CurrencyUnit> getCurrencies(String namespace);

    /**
     * Checks if a given namespace is known to this provider.
     *
     * @param namespace the namespace id, not {@code null}.
     * @return {@code true} if the namespace is served by this provider.
     */
    public boolean isNamespaceAvailable(String namespace);

    /**
     * Reverse mapping of a currencyCode to its namespaces.
     *
     * @param currencyCode the currencyCode, not {@code null}
     * @return the set of namespaces for the given code, never {@code null}.
     */
    public Set<String> getNamespaces(String currencyCode);

}
