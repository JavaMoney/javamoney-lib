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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.ibm.icu.util.Region;
import com.ibm.icu.util.Region.RegionType;
import com.ibm.icu.util.TimeZone;

/**
 * Implementation for {@link com.ibm.icu.util.Region} based on ICU4J's
 * {@link com.ibm.icu.util.Region}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class ICURegion {

	private Region icuRegion;
	private Locale countryLocale;
	private static final List<Class> REGION_DATATYPES = Arrays
			.asList(new Class[] { ICURegion.class });

	public ICURegion(Region icuRegion) {
		if (icuRegion == null) {
			throw new IllegalArgumentException("icuRegion required.");
		}
		this.icuRegion = icuRegion;
		this.countryLocale = new Locale("", icuRegion.toString());
	}

	public RegionType getRegionType() {
		return icuRegion.getType();
	}

	public String getRegionCode() {
		// returns region id!
		return icuRegion.toString();
	}

	public int getNumericCode() {
		return icuRegion.getNumericCode();
	}

	public Collection<String> getTimezoneIds() {
		String[] timezones = TimeZone.getAvailableIDs(getRegionCode());
		return Arrays.asList(timezones);
	}

	public String getDisplayName(Locale locale) {
		String name = this.countryLocale.getDisplayCountry(locale);
		if (name == null) {
			name = this.getRegionCode();
		}
		return name;
	}

	public Region getIcuRegion() {
		return this.icuRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String name = getDisplayName(Locale.getDefault());
		if (name == null) {
			name = "IcuRegion";
		}
		return name + " [code: " + icuRegion.toString() + ", type: "
				+ getRegionType().name() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((icuRegion == null) ? 0 : icuRegion.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ICURegion other = (ICURegion) obj;
		if (icuRegion == null) {
			if (other.icuRegion != null) {
				return false;
			}
		} else if (!icuRegion.equals(other.icuRegion)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Region#getLocale()
	 */
	public Locale getLocale() {
		return this.countryLocale;
	}


}
