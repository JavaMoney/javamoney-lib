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
package org.javamoney.validity.spi;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Set;

import org.javamoney.validity.ValidityInfo;
import org.javamoney.validity.ValidityQuery;
import org.javamoney.validity.ValidityType;

/**
 * This interface must be implemented by components that provide validity data
 * (history of data). It is the responsibility of the registered
 * {@link ValiditiesSingletonSpi} to load the and manage the instances of
 * {@link ValidityProviderSpi}. Depending on the runtime environment,
 * implementations may be loaded using the {@link ServiceLoader}. But also
 * alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, and should not be
 * contextual in a EE context. Contextual behaviour should be implemented by the
 * {@link ValiditiesSingletonSpi}.
 *
 * @author Anatole Tresch
 */
public interface ValidityProviderSpi{

    /**
     * Access the (unique) provider id.
     *
     * @return the validity provider id, never {@code null}, not empty. The
     * identifier must be unique, since it may also be used to
     * explicitly select validity information from a certain provider.
     * @see {@link ValidityQuery#getValiditySource()}
     * @see {@link ValidityQuery#withValiditySource(String)}
     */
    public String getProviderId();

    /**
     * Return the {@link ValidityType} instances that this instance is
     * supporting, this can be used for determining which providers may of
     * result for a given query.
     *
     * @return the set of supported {@link ValidityType}s, never {@code null}.
     */
    public Set<ValidityType> getValidityTypes();

    /**
     * Return the item types that this provider instance is supporting, this is
     * used for determining, which providers must be called for a given
     * {@link ValidityQuery} query.
     *
     * @return the set of supported item types, never {@code null}.
     * @see {@link ValidityQuery#getItemType()}
     */
    public Set<Class<?>> getItemTypes();

    /**
     * Access all {@link ValidityInfo} for the given query.
     *
     * @param query the related validity query.
     * @return the {@link ValidityInfo} instances found, never {@code null}.
     */
    public <T> Collection<ValidityInfo<T>> getValidityInfo(ValidityQuery<T> query);
}
