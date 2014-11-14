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
package org.javamoney.regions.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.spi.Bootstrap;

import org.javamoney.regions.Region;
import org.javamoney.regions.RegionTreeNode;
import org.javamoney.regions.RegionType;
import org.javamoney.regions.spi.ExtendedRegionDataProviderSpi;
import org.javamoney.regions.spi.RegionProviderSpi;
import org.javamoney.regions.spi.RegionTreeProviderSpi;
import org.javamoney.regions.spi.RegionsSingletonSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link RegionsSingletonSpi} implementation. It is extended
 * for different runtime scenarios, hereby allowing the spi implementation
 * loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class DefaultRegionsSingleton implements RegionsSingletonSpi{
	/** The logger used. */
	private static final Logger LOG = LoggerFactory
			.getLogger(DefaultRegionsSingleton.class);

	

	/**
	 * Access a {@link Region} by {@link RegionType} and its numeric id.
	 * 
	 * @param type
	 *            The region's type
	 * @param numericId
	 *            the region's numeric id
	 * @return the region found, never {@code null}
	 * @throws IllegalArgumentException
	 *             , if no such region could be found.
	 */
	@Override
	public Region getRegion(RegionType type, int numericId) {
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Region reg = prov.getRegion(type, numericId);
			if (reg != null) {
				return reg;
			}
		}
		throw new IllegalArgumentException("So such reagion " + type + ':'
				+ numericId);
	}

	/**
	 * Access a {@link Region} by {@link RegionType} and its textual id.
	 * 
	 * @param type
	 *            The region's type
	 * @param code
	 *            the region's textual id
	 * @return the region found, never {@code null}
	 * @throws IllegalArgumentException
	 *             , if no such region could be found.
	 */
	@Override
	public Region getRegion(RegionType type, String code) {
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Region reg = prov.getRegion(type, code);
			if (reg != null) {
				return reg;
			}
		}
		throw new IllegalArgumentException("So such reagion " + type + ':'
				+ code);
	}

	/**
	 * Get all {@link RegionType} instances that are known.
	 * 
	 * @see RegionProviderSpi#getRegionTypes()
	 * @return the {@link RegionType} instances defined by the registered
	 *         {@link RegionProviderSpi} instances.
	 */
	@Override
	public Set<RegionType> getRegionTypes() {
		Set<RegionType> result = new HashSet<RegionType>();
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Collection<RegionType> regionTypes = prov.getRegionTypes();
			if (regionTypes == null || regionTypes.isEmpty()) {
				LOG.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(regionTypes);
			}
		}
		return result;
	}

	/**
	 * Get all {@link Region} instances of a given {@link RegionType}.
	 * 
	 * @param type
	 *            the region's type
	 * @return all {@link Region} instances with the given type.
	 */
	@Override
	public Collection<Region> getRegions(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Collection<Region> regions = prov.getRegions(type);
			if (regions == null || regions.isEmpty()) {
				LOG.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(regions);
			}
		}
		return result;
	}

	/**
	 * Get the region by its locale.
	 * 
	 * @param locale
	 *            The locale
	 * @return the region found, or {@code null}
	 */
	@Override
	public Region getRegion(Locale locale) {
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Region region = prov.getRegion(locale);
			if (region != null) {
				return region;
			}
		}
		return null;
	}
	
	/**
	 * Access a set of Region instances that are defined to be graph root
	 * regions, which are identifiable entry points into the region graph.
	 * 
	 * @return the root graph {@link Region}s defined by this spi, not null.
	 */
	@Override
	public RegionTreeNode getRegionTree(String treeId) {
		for (RegionTreeProviderSpi prov : Bootstrap.getServices(RegionTreeProviderSpi.class)) {
			try {
				if (treeId.equals(prov.getTreeId())) {
					RegionTreeNode node = prov.getRegionTree();
					if (node == null) {
						LOG.error("Error accessing RegionTree: " + treeId
								+ " from " + prov.getClass().getName()
								+ ": provider returned null.");
						return null;
					}
					return node;
				}
			} catch (Exception e) {
				LOG.error("Error initializing RegionTreeProviderSpi: "
						+ prov.getClass().getName(), e);
			}
		}
		return null;
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	private Collection<Region> getAllRegions() {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : Bootstrap.getServices(RegionProviderSpi.class)) {
			Collection<RegionType> types = prov.getRegionTypes();
			for (RegionType t : types) {
				Collection<Region> regions = prov.getRegions(t);
				if (regions == null || regions.isEmpty()) {
					LOG.warn("Provider did not provide any regions: "
							+ prov.getClass().getName());
				} else {
					result.addAll(regions);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<Class> getExtendedRegionDataTypes(Region region) {
		Set<Class> result = new HashSet<>();
		for(ExtendedRegionDataProviderSpi spi: Bootstrap.getServices(ExtendedRegionDataProviderSpi.class)){
			result.addAll(spi.getExtendedRegionDataTypes(region));
		}
		return result;
	}

	@Override
	public <T> T getExtendedRegionData(Region region, Class<T> type) {
		for(ExtendedRegionDataProviderSpi spi: Bootstrap.getServices(ExtendedRegionDataProviderSpi.class)){
			if(spi.getExtendedRegionDataTypes(region).contains(type)){
				return spi.getExtendedRegionData(region, type);
			}
		}
		return null;
	}

	@Override
	public Set<String> getRegionTreeIds() {
		Set<String> result = new HashSet<>();
		for (RegionTreeProviderSpi prov : Bootstrap.getServices(RegionTreeProviderSpi.class)) {
			try {
				result.add(prov.getTreeId());
			} catch (Exception e) {
				LOG.error("Error accessing RegionTreeProviderSpi: "
						+ prov.getClass().getName(), e);
			}
		}
		return result;
	}

}
