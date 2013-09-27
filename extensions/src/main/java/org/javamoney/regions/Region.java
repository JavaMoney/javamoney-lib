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
package org.javamoney.regions;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

import javax.money.CurrencyUnit;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).<br/>
 * Hereby a {@link Region}, similarly to {@link CurrencyUnit} does only provide
 * a representation type, whereas the validity and existence of regions must be
 * quiried from {@link RegionValidity} provider.
 * <p>
 * Instances of this class are required to be thread-safe, immutable and
 * {@link Serializable} and {@link Comparable}. Comparison hereby should rely on
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
	 * Additionally all ids returned should be known by {@link TimeZone}.
	 * 
	 * @return the timezone ids of this region, never {@code null}.
	 */
	public Collection<String> getTimezoneIds();

	/**
	 * Return according {@link Locale}, if possible.
	 * 
	 * @return the corresponding {@link Locale} for that {@link Region}, may
	 *         also return {@code null}.
	 */
	public Locale getLocale();

}
