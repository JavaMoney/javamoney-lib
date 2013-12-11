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
import javax.money.bootstrap.Bootstrap;

import org.javamoney.regions.AbstractRegionProviderService;
import org.javamoney.regions.spi.RegionProviderSpi;


/**
 *
 * @author Anatole
 * @author Werner
 */
public class SERegionProviderService extends AbstractRegionProviderService {

    private List<RegionProviderSpi> regionProviderSpis;
    
    @Override
    protected Iterable<RegionProviderSpi> getRegionProviderSpis() {
        if(regionProviderSpis==null){
            List<RegionProviderSpi> load = new ArrayList<RegionProviderSpi>();
            for (RegionProviderSpi regionProviderSpi : Bootstrap.getServiceProvider()
            		.getServices(RegionProviderSpi.class)) {
                load.add(regionProviderSpi);
            }
            this.regionProviderSpis = load;
        }
        return regionProviderSpis;
    }
    
}
