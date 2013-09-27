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
import java.util.Locale;
import java.util.ServiceLoader;

import org.javamoney.regions.Region;
import org.javamoney.regions.RegionType;

/**
 * Implementation of this interface add additional {@link Region}s to the
 * regions API. Each {@link RegionProviderSpi} may hereby serve several
 * {@link RegionType}s.<br/>
 * It is the responsibility of the registered {@link RegionsSingletonSpi} to
 * load the and manage the instances of {@link RegionProviderSpi}. Depending on
 * the runtime environment, implementations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface RegionProviderSpi {

	/**
	 * Returns all {@link RegionType}s provided by this
	 * {@link RegionProviderSpi} instance, hereby it is possible that several
	 * providers may provide {@link Region}s for the same {@link RegionType}, as
	 * long as they are unique related to its code and numderic id (if defined).
	 * 
	 * @return the {@link RegionType}s for which this provider provides regions,
	 *         never {@code null}.
	 */
	public Collection<RegionType> getRegionTypes();

	/**
	 * Access all regions provided for the given {@link RegionType}.
	 * 
	 * @param type
	 *            The required region type.
	 * @return the regions to be provided, never {@code null}.
	 */
	public Collection<Region> getRegions(RegionType type);

	/**
	 * Access a {@link Region}.
	 * 
	 * @param type
	 *            The required region type.
	 * @param identifier
	 *            The region's id.
	 * @return The corresponding region, or {@code null}.
	 * 
	 */
	public Region getRegion(RegionType type, String identifier);

	/**
	 * Access a {@link Region}.
	 * 
	 * @param numericId
	 *            The region's numeric id.
	 * @param type
	 *            The required region type.
	 * @return The corresponding region, or {@code null}.
	 */
	public Region getRegion(RegionType type, int numericId);

	/**
	 * Access a region using a {@link Locale}.
	 * 
	 * @param locale
	 *            The correspoding country {@link Locale}.
	 * @return the corresponding region, or {@code null}.
	 */
	public Region getRegion(Locale locale);

}
