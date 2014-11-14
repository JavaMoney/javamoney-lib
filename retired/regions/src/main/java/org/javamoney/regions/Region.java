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
package org.javamoney.regions;

import java.util.Collection;
import java.util.Locale;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).<br/>
 * Hereby a {@link org.javamoney.regions.Region}, similarly to {@link javax.money.CurrencyUnit} does only provide
 * a representation type, whereas the validity and existence of regions must be
 * quiried from {@link RegionValidity} provider.
 * <p>
 * Instances of this class are required to be thread-safe, immutable and
 * {@link java.io.Serializable} and {@link Comparable}. Comparison hereby should rely on
 * <ul>
 * <li>The {@link RegionType}
 * <li>The region code
 * </ul>
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface Region {

	/**
	 * Get the region's type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public RegionType getRegionType();

	/**
	 * Access the region's code. The code is unique in combination with the
	 * region type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public String getRegionCode();

	/**
	 * Get the region's numeric code. If not defined -1 is returned.
	 * 
	 * @return the numeric region code, or {@code -1}.
	 */
	public int getNumericRegionCode();

	/**
	 * Return the time zones valid for this region (in the long form, e.g.
	 * Europe/Berlin). If the region has subregions, by default, the timezones
	 * returned should be the transitive closure of all timezones of all child
	 * regions. Nevertheless there might be use cases were the child regions
	 * must not transitively define the parents timezones, so transitivity is
	 * not enforced by this JSR.<br/>
	 * Additionally all ids returned should be known by {@link java.util.TimeZone}.
	 * 
	 * @return the timezone ids of this region, never {@code null}.
	 */
	public Collection<String> getTimezoneIds();

	/**
	 * Return according {@link java.util.Locale}, if possible.
	 * 
	 * @return the corresponding {@link java.util.Locale} for that {@link org.javamoney.regions.Region}, may
	 *         also return {@code null}.
	 */
	public Locale getLocale();

}
