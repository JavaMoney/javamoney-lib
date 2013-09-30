/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javamoney.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.javamoney.regions.AbstractRegionTreeProviderService;
import org.javamoney.regions.spi.RegionTreeProviderSpi;

/**
 *
 * @author Anatole
 */
public class SERegionTreeProviderService extends AbstractRegionTreeProviderService {

    private List<RegionTreeProviderSpi> regionTreeProviderSpis;
    
    @Override
    protected Iterable<RegionTreeProviderSpi> getRegionTreeProviderSpis() {
        if(regionTreeProviderSpis==null){
            List<RegionTreeProviderSpi> load = new ArrayList<RegionTreeProviderSpi>();
            ServiceLoader<RegionTreeProviderSpi> services = ServiceLoader.load(RegionTreeProviderSpi.class);
            for (RegionTreeProviderSpi regionForestProviderSpi : services) {
                load.add(regionForestProviderSpi);
            }
            this.regionTreeProviderSpis = load;
        }
        return regionTreeProviderSpis;
    }
    
}
