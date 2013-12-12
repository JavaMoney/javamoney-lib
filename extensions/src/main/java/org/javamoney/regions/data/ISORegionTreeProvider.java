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
package org.javamoney.regions.data;

import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;

import org.javamoney.regions.Region;
import org.javamoney.regions.RegionTreeNode;
import org.javamoney.regions.RegionType;
import org.javamoney.regions.spi.BuildableRegionNode;
import org.javamoney.regions.spi.RegionProviderSpi;
import org.javamoney.regions.spi.RegionTreeProviderSpi;
import org.javamoney.regions.spi.BuildableRegionNode.Builder;


/**
 * Region Tree provider that provides all ISO countries, defined by
 * {@link Locale#getISOCountries()} using their 2-letter ISO country code under
 * {@code ISO}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ISORegionTreeProvider implements RegionTreeProviderSpi {

	private BuildableRegionNode regionTree;

	// ISO/...

	@Override
	public String getTreeId() {
		return "ISO";
	}

	@Override
	public void init(Map<Class, RegionProviderSpi> providers) {
		Builder treeBuilder = new BuildableRegionNode.Builder(new SimpleRegion(
				"ISO"));

		ISORegionProvider regionProvider = (ISORegionProvider) providers
				.get(ISORegionProvider.class);
		for (String country : Locale.getISOCountries()) {
			Region region = regionProvider.getRegion(RegionType.of("ISO"),
					country);
			Builder nodeBuilder = new BuildableRegionNode.Builder(region);
			treeBuilder.addChildRegions(nodeBuilder.build());
		}
		regionTree = treeBuilder.build();
	}

	@Override
	public RegionTreeNode getRegionTree() {
		return regionTree;
	}

}