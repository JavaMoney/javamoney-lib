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
 * {@link java.util.Locale#getISOCountries()} using their 3-letter ISO country code under
 * {@code ISO3}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ISO3RegionTreeProvider implements RegionTreeProviderSpi {

	private BuildableRegionNode regionTree;

	// ISO3/...

	@Override
	public String getTreeId() {
		return "ISO3";
	}

	@Override
	public void init(Map<Class, RegionProviderSpi> providers) {
		Builder treeBuilder = new BuildableRegionNode.Builder(new SimpleRegion(
				"ISO3"));
		ISORegionProvider regionProvider = (ISORegionProvider) providers
				.get(ISORegionProvider.class);
		for (String country : Locale.getISOCountries()) {
			Locale locale = new Locale("", country);
			Region region = regionProvider.getRegion(RegionType.of("ISO"),
					locale.getISO3Country());
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