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
package org.javamoney.regions.spi;

import java.util.Collection;

import org.javamoney.regions.Region;

/**
 * Implementation of this interface provide arbitrary additional data for a
 * region.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface ExtendedRegionDataProviderSpi {

	/**
	 * Get the extended data types, that can be accessed from this
	 * {@link Region} by calling {@link #getRegionData(Class)}.
	 * 
	 * @param region
	 *            the region for which addition data is requested.
	 * @return the collection of supported region data, may be {@code empty} but
	 *         never {@code null}.
	 */
	public Collection<Class> getExtendedRegionDataTypes(Region region);

	/**
	 * Access the additional region data, using its type.
	 * <p>
	 * Note different to the API this method does never throw an
	 * {@link IllegalArgumentException} when the required type is not supported,
	 * but simply should return {@code null}.
	 * 
	 * @param region
	 *            the region for which addition data is requested.
	 * @param type
	 *            The region data type, not {@code null}.
	 * @return the corresponding data item, or {@code null} if the type passed
	 *         is not supported.
	 */
	public <T> T getExtendedRegionData(Region region, Class<T> type);

}
