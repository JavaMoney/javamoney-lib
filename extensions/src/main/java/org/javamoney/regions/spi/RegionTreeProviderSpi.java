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

import java.util.Map;
import java.util.ServiceLoader;

import org.javamoney.regions.Region;
import org.javamoney.regions.RegionTreeNode;

/**
 * Implementation of this interface provide a region tree, identified by
 * {@link #getTreeId()}. A provider hereby can only provide one region tree and
 * {@link #getTreeId()} must be unique within the all region trees in a runtime
 * context.<br/>
 * It is the responsibility of the registered {@link RegionsSingletonSpi} to
 * load the and manage the instances of {@link RegionTreeProviderSpi}. Depending
 * on the runtime environment, implementations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but should not be
 * contextual in a EE context (this should be done by the
 * {@link RegionsSingletonSpi}).
 * 
 * @author Anatole Tresch
 */
public interface RegionTreeProviderSpi {

	/**
	 * Get the id of the tree provided by this provider.
	 * 
	 * @return the id of the tree, provided by this tree provider, never
	 *         {@code null} and not empty.
	 */
	public String getTreeId();

	/**
	 * Initialize the {@link RegionTreeProviderSpi} provider.
	 * 
	 * @param regionProviders
	 *            the region providers loaded, to be used for accessing
	 *            {@link Region} entries to be organized in a
	 *            {@link RegionTreeNode} tree structure.
	 */
	public void init(Map<Class, RegionProviderSpi> regionProviders);

	/**
	 * Access the root {@link RegionTreeNode} of the region tree provided.
	 * 
	 * @return the root node, never {@code null}.
	 */
	public RegionTreeNode getRegionTree();

}
