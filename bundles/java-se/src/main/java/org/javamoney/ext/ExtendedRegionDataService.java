/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.javamoney.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.money.bootstrap.Bootstrap;

import org.javamoney.regions.AbstractExtendedRegionDataService;
import org.javamoney.regions.spi.ExtendedRegionDataProviderSpi;

/**
 * 
 * @author Anatole
 */
public class ExtendedRegionDataService extends
		AbstractExtendedRegionDataService {

	private List<ExtendedRegionDataProviderSpi> regionDataProviderSpis;

	@Override
	protected Iterable<ExtendedRegionDataProviderSpi> getExtendedRegionDataProviderSpis() {
		if (regionDataProviderSpis == null) {
			List<ExtendedRegionDataProviderSpi> load = new ArrayList<ExtendedRegionDataProviderSpi>();
			for (ExtendedRegionDataProviderSpi regionDataProviderSpi : Bootstrap
					.getServices(ExtendedRegionDataProviderSpi.class)) {
				load.add(regionDataProviderSpi);
			}
			this.regionDataProviderSpis = load;
		}
		return regionDataProviderSpis;
	}

}
