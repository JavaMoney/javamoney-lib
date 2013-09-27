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
import org.javamoney.validity.Validities;
import org.javamoney.validity.ValidityInfo;
import org.javamoney.validity.ValidityQuery;
import org.javamoney.validity.ValidityType;

/**
 * This interface models the service provider interface to which the method
 * calls of the {@link Validities} singleton are forwarded. It must be
 * registered using the JDK {@link ServiceLoader}, hereby only one instance can
 * be registered within the whole VM. In an EE context the registered
 * implementation may switch the execution context based on the enterprise
 * application running.<br/>
 * Instances of this class are responsible for loading and managing of
 * {@link ValidityProviderSpi} and {@link RelatedValidityProviderSpi}.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface ValiditiesSingletonSpi {

	/**
	 * Access the identifiers of the {@link RelatedValidityProviderSpi}
	 * instances, which deliver related validity data for the given {@code type}
	 * and its relation to {@code relatedType}.
	 * 
	 * @param type
	 *            The item type class.
	 * @param relatedType
	 *            The class of the related type
	 * @return the {@link RelatedValidityInfo} provider ids registered for the
	 *         given {@code type} and {@code relatedType}, never {@code null}.
	 */
	public <T, R> Set<String> getRelatedValidityProviderIds(Class<T> type,
			Class<R> relatedType);

	/**
	 * Access the ids of the providers, which deliver validity data for the
	 * given type.
	 * 
	 * @param type
	 *            The item type
	 * @return the according {@link ValidityProviderSpi} provider ids registered
	 *         for the given {@code type}, never {@code null}.
	 */
	public <T> Set<String> getValidityProviderIds(Class<T> type);

	/**
	 * Access the related types that are supported by the currently registered
	 * {@link RelatedValidityProviderSpi} instances.
	 * 
	 * @see RelatedValidityProviderSpi#getRelatedItemTypes(Class)
	 * @see RelatedValidityQuery#getRelatedToType()
	 * @param type
	 *            The item type
	 * @return the according relatedTo types, never {@code null}.
	 */
	public <T> Set<Class> getRelatedValidityRelationTypes(Class<T> type);

	/**
	 * Access the related types that are supported by the currently registered
	 * {@link RelatedValidityProviderSpi} instances.
	 * 
	 * @see RelatedValidityProviderSpi#getItemTypes()
	 * @see RelatedValidityQuery#getItemType()
	 * @return the according relatedTo types, never {@code null}.
	 */
	public Set<Class> getRelatedValidityItemTypes();

	/**
	 * Access the item types that are supported by the currently registered
	 * {@link ValidityProviderSpi} instances.
	 * 
	 * @see ValidityProviderSpi#getItemTypes()
	 * @see ValidityQuery#getItemType()
	 * @return the according relatedTo types, never {@code null}.
	 */
	public Set<Class> getValidityItemTypes();

	/**
	 * Access the supported {@link ValidityType} instances for the given item
	 * type, <b>when accessing {@link RelatedValidityInfo}</b>.
	 * 
	 * @see #getRelatedValidityInfo(RelatedValidityQuery)
	 * @param type
	 *            the item type.
	 * @return the supported {@link ValidityType} instances, never {@code null}
	 */
	public <T, R> Set<ValidityType> getRelatedValidityTypes(Class<T> type,
			Class<R> relatedType);

	/**
	 * Access the supported {@link ValidityType} instances for the given item
	 * type, <b>when accessing {@link ValidityInfo}</b>.
	 * 
	 * @see #getValidityInfo(ValidityQuery)
	 * @param type
	 *            the item type.
	 * @return the supported {@link ValidityType} instances, never {@code null}
	 */
	public <T> Set<ValidityType> getValidityTypes(Class<T> type);

	/**
	 * Access all {@link RelatedValidityInfo} for the given {@code query}.
	 * 
	 * @param query
	 *            the {@link RelatedValidityQuery} query that defines which
	 *            validity information should be returned.
	 * @return the {@link RelatedValidityInfo} instances found, never
	 *         {@code null}.
	 */
	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query);

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param query
	 *            the query that defines which {@link ValidityInfo} should be
	 *            returned.
	 * @return the {@link ValidityInfo} instances matching, never {@code null}.
	 */
	public <T> Collection<ValidityInfo<T>> getValidityInfo(
			ValidityQuery<T> query);

}
