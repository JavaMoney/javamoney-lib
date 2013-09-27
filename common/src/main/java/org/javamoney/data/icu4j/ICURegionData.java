/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package org.javamoney.data.icu4j;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import com.ibm.icu.util.Region;
import com.ibm.icu.util.Region.RegionType;

/**
 * RegionProvider implementation based on the data returned by ICU4J.
 * 
 * @author Anatole Tresch
 */
public class ICURegionData {

	private static final ICURegionData INSTANCE = createInstance();
	
	private Set<RegionType> regionTypes = new HashSet<RegionType>();

	private Map<String, ICURegion> regions = new HashMap<String, ICURegion>();

	// CLDR/world/continents/territories

	private ICURegionData() {
		reload();
	}
	
	public static ICURegionData get() {
		return INSTANCE;
	}
	
	private static ICURegionData createInstance() {
		try {
			return new ICURegionData();
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error creating CLDR ICURegionProvider.", e);
		}
	}

	private void reload() {
		regionTypes.clear();
		ICURegion world = null;
		for (com.ibm.icu.util.Region.RegionType rt : com.ibm.icu.util.Region.RegionType
				.values()) {
			this.regionTypes.add(rt);
			Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
					.getAvailable(com.ibm.icu.util.Region.RegionType
							.valueOf(rt.name()));
			for (com.ibm.icu.util.Region icuRegion : icuRegions) {
				RegionType type = icuRegion.getType();
				regionTypes.add(type);
				ICURegion region = new ICURegion(icuRegion);
				regions.put(region.getRegionCode(), region);
				if (region.getRegionType().equals(RegionType.WORLD)) {
					world = region;
				}
			}
		}
		Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
				.getAvailable(com.ibm.icu.util.Region.RegionType.WORLD);
	}

	public Collection<RegionType> getRegionTypes() {
		return Collections.unmodifiableSet(regionTypes);
	}

	public ICURegion getRegion(RegionType type, String code) {
		com.ibm.icu.util.Region icuRegion = com.ibm.icu.util.Region
				.getInstance(code);
		if (icuRegion != null) {
			ICURegion region = this.regions.get(code);
			if (region.getRegionType().equals(type)) {
				return region;
			}
		}
		return null;
	}
	
	public ICURegion getRegion(String code) {
		com.ibm.icu.util.Region icuRegion = com.ibm.icu.util.Region
				.getInstance(code);
		return this.regions.get(code);
	}

	public Collection<ICURegion> getRegions(RegionType type) {
		Set<ICURegion> result = new HashSet<ICURegion>();
		for (ICURegion region : regions.values()) {
			if (region.getRegionType().equals(type)) {
				result.add(region);
			}
		}
		return result;
	}

	public ICURegion getRegion(RegionType type, int numericId) {
		for (ICURegion region : regions.values()) {
			if (region.getRegionType().equals(type)
					&& region.getNumericRegionCode() == numericId) {
				return region;
			}
		}
		return null;
	}

	public ICURegion getRegion(Locale locale) {
		return regions.get(locale.getCountry());
	}

}