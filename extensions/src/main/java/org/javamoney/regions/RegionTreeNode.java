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

import org.javamoney.calc.function.Predicate;


/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).<br/>
 * According root instances (trees) can be accessed by calling
 * {@link Regions#getRegionTree(String)}. By default, ISO countries accessible
 * with 2-digit or 3-digit codes must be provided by default.
 * <p>
 * Instances if this class are required to be thread safe, however it is not
 * required they are {@link Serializable}. Additionally instances should be
 * comparable based on on the comparable {@link Region} included.
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface RegionTreeNode {

	/**
	 * Get the corresponding region.
	 * 
	 * @return the region, never {@code null}.
	 */
	public Region getRegion();

	/**
	 * Get the direct parent region of this region.
	 * 
	 * @return the parent region, or {@code null}, if this region has no parent
	 *         (is a root region).
	 */
	public RegionTreeNode getParent();

	/**
	 * Access all direct child regions.
	 * 
	 * @return all direct child regions, never {@code null}.
	 */
	public Collection<RegionTreeNode> getChildren();

	/**
	 * Determines if the given region is contained within this region tree.
	 * 
	 * @param region
	 *            the region being looked up, null hereby is never contained.
	 * @return {@code true} if the given region is a direct or indirect child of
	 *         this region instance.
	 */
	public boolean contains(Region region);

	/**
	 * Select the parent region with the given type. This method will navigate
	 * up the region tree and select the first parent encountered that has the
	 * given region type.
	 * 
	 * @param predicate
	 *            the selecting filter, {@code null} will return the direct
	 *            parent, if any.
	 * @return the region found, or {@code null}.
	 */
	public RegionTreeNode selectParent(Predicate<Region> predicate);

	/**
	 * Select a collection of regions selected by the given filter.
	 * 
	 * @param predicate
	 *            the region selector, {@code null} will return all regions.
	 * @return the regions selected.
	 */
	public Collection<RegionTreeNode> select(Predicate<Region> predicate);

	/**
	 * Access a {@link Region} using the region path, which allows access of a
	 * {@link Region} from the tree, e.g. {@code WORLD/EUROPE/GERMANY} or
	 * {@code STANDARDS/ISO/GER}.
	 * 
	 * @param path
	 *            the path to be accessed, not {@code null}.
	 * @return the {@link Region} found, or {@code null}.
	 */
	public RegionTreeNode getRegionTree(String path);

}
