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
package org.javamoney.regions.serv;

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
            for (RegionTreeProviderSpi regionTreeProviderSpi : services) {
                load.add(regionTreeProviderSpi);
            }
            this.regionTreeProviderSpis = load;
        }
        return regionTreeProviderSpis;
    }
    
}
