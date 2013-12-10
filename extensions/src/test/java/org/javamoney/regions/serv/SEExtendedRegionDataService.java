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

import javax.money.bootstrap.Bootstrap;

import org.javamoney.regions.AbstractExtendedRegionDataService;
import org.javamoney.regions.spi.ExtendedRegionDataProviderSpi;

/**
 * 
 * @author Anatole
 */
public class SEExtendedRegionDataService extends
		AbstractExtendedRegionDataService {

	private List<ExtendedRegionDataProviderSpi> regionDataProviderSpis;

	@Override
    protected Iterable<ExtendedRegionDataProviderSpi> getExtendedRegionDataProviderSpis() {
        if(regionDataProviderSpis==null){
            List<ExtendedRegionDataProviderSpi> load = new ArrayList<ExtendedRegionDataProviderSpi>();
            for (ExtendedRegionDataProviderSpi regionDataProviderSpi: Bootstrap.getServices(ExtendedRegionDataProviderSpi.class)) {
                load.add(regionDataProviderSpi );
            }
            this.regionDataProviderSpis = load;
        }
        return regionDataProviderSpis;
    }
}
