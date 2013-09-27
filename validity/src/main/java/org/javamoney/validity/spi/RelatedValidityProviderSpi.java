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

import org.javamoney.validity.RelatedValidityInfo;
import org.javamoney.validity.RelatedValidityQuery;
import org.javamoney.validity.ValidityType;

/**
 * This interface must be implemented by components that provide related
 * validity data (history of relationships between data). It is the
 * responsibility of the registered {@link ValiditiesSingletonSpi} to load the
 * and manage the instances of {@link RelatedValidityProviderSpi}. Depending on
 * the runtime environment, implementations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface RelatedValidityProviderSpi {

	/**
	 * Access the (unique) provider id.
	 * 
	 * @see {@link RelatedValidityQuery#getValiditySource()}
	 * @see {@link RelatedValidityQuery#withValiditySource(String)}
	 * @return the validity provider id, never {@code null}, not empty. The
	 *         identifier must be unique, since it may also be used to
	 *         explicitly select validity information from a certain provider.
	 */
	public String getProviderId();

	/**
	 * Return the {@link ValidityType} instances that this instance is
	 * supporting, this is used for determining which providers have to be
	 * called for evaluating a given {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getValidityType()}
	 * @see {@link RelatedValidityQuery#withValidityType(ValidityType)}
	 * @param itemType
	 *            the item type, not {@code null}.
	 * @param relatedType
	 *            the type of the related item, not {@code null}.
	 * @return the set of supported {@link ValidityType}s, never {@code null}.
	 */
	public Set<ValidityType> getValidityTypes(Class itemType, Class relatedType);

	/**
	 * Return the item types that this provider instance is supporting, this is
	 * used for determining, which providers must be called for a given
	 * {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getItemType()}
	 * @return the set of supported item types, never {@code null}.
	 */
	public Set<Class> getItemTypes();

	/**
	 * Return the related item types that this instance is supporting, given the
	 * base item type. This information is used for determining which providers
	 * must be called for a given {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getRelatedToType()}
	 * 
	 * @param itemType
	 *            the item's type, which can be related to the types returned.
	 * @return the set of supported related item types, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the itemType is not one of the types returned by
	 *             {@link #getItemTypes()}
	 */
	public Set<Class> getRelatedItemTypes(Class<?> itemType);

	/**
	 * Access all {@link RelatedValidityInfo} for the given query.
	 * 
	 * @param query
	 *            the related validity query.
	 * @return the {@link RelatedValidityInfo} instances found, never
	 *         {@code null}.
	 */
	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query);

}
