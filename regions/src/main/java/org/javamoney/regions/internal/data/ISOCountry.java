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
package org.javamoney.regions.internal.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.javamoney.regions.Region;
import org.javamoney.regions.RegionType;

import com.ibm.icu.util.TimeZone;

/**
 * Implementation of a {@link Region} based on {@link java.util.Locale}, using the ISO
 * 2-letter code as Region code.
 * 
 * @author Anatole Tresch
 */
public class ISOCountry implements Region {

	private Locale locale;
	private RegionType regionType;

	public ISOCountry(Locale locale, RegionType rt) {
		this.locale = locale;
		this.regionType = rt;
	}

	@Override
	public RegionType getRegionType() {
		return regionType;
	}

	@Override
	public String getRegionCode() {
		return locale.getCountry();
	}

	public String getISO3Code() {
		return locale.getISO3Country();
	}

	@Override
	public int getNumericRegionCode() {
		return com.ibm.icu.util.Region.getInstance(getRegionCode())
				.getNumericCode();
	}

	@Override
	public Collection<String> getTimezoneIds() {
		return Arrays.asList(TimeZone.getAvailableIDs(locale.getCountry()));
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return locale.getDisplayName() + " [ISO: code=" + locale.getCountry()
				+ '/' + locale.getISO3Country() + ", regionType="
				+ regionType.getId()
				+ "]";
	}

}
