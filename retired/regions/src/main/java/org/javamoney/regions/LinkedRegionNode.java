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

import org.javamoney.calc.function.MonetaryPredicate;

/**
 * This models a subtree within a {@link RegionTreeNode}, that is transparently
 * modeled/provided by another subtree within the global region trees. This can
 * be useful to fill up the possible child values with regions already existing.
 * E.g. A sales representative's organization may contain several countries,
 * defined in detail by ISO, so instead of copying the ISO values they can be
 * linked to the corresponding ISO area within the global region forest.<br/>
 * Since sometimes not all linked values atr applicable an additional
 * {@link RegionFilter} can be applied.
 * 
 * @author Anatole Tresch
 */
public interface LinkedRegionNode extends RegionTreeNode {

	/**
	 * This method returns the effective tree path, starting with a current root
	 * region, to which this node links. This allows to link an arbitrary region
	 * subgraph as child node of the given tree.
	 * 
	 * @see #getLinkedFilter()
	 * @return the path to the subgraph linkded, or {@code null}.
	 */
	public String getLinkedPath();

	/**
	 * This filter allows to filter out children, from the subgraph linked in.
	 * 
	 * @see #getLinkedPath()
	 * @return the filter used, or {@code null}.
	 */
	public MonetaryPredicate<Region> getLinkedFilter();
}
