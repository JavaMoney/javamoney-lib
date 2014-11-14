/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package org.javamoney.regions.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
		for (RegionType rt : RegionType
				.values()) {
			this.regionTypes.add(rt);
			Set<Region> icuRegions = Region
					.getAvailable(RegionType
							.valueOf(rt.name()));
			for (Region icuRegion : icuRegions) {
				RegionType type = icuRegion.getType();
				regionTypes.add(type);
				ICURegion region = new ICURegion(icuRegion);
				regions.put(region.getRegionCode(), region);
				if (region.getRegionType().equals(RegionType.WORLD)) {
					world = region;
				}
			}
		}
		Set<Region> icuRegions = Region
				.getAvailable(RegionType.WORLD);
	}

	public Collection<RegionType> getRegionTypes() {
		return Collections.unmodifiableSet(regionTypes);
	}

	public ICURegion getRegion(RegionType type, String code) {
		Region icuRegion = Region
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
		Region icuRegion = Region
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
					&& region.getNumericCode() == numericId) {
				return region;
			}
		}
		return null;
	}

	public ICURegion getRegion(Locale locale) {
		return regions.get(locale.getCountry());
	}

}