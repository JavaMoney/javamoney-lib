/*
 * Copyright (c) 2012, 2015, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *
 *
 * Contributors: Anatole Tresch - initial version.
 * 				 Werner Keil - adjusted to 1.0.
 */
package org.javamoney.cdi.internal;

import static org.javamoney.cdi.internal.Constants.PRIO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.money.spi.ServiceProvider;

import org.javamoney.moneta.internal.PriorityAwareServiceProvider;

/**
 * Overriding ServiceProvider that actually tries to satisfy component requests from CDI,
 * where possible. Additionally ServiceLoader based service are loaded and are returned
 * ONLY, when not the same service is loaded as well in CDI.
 */
@Priority(PRIO)
public class CDISEServiceProvider implements ServiceProvider {

    private static final Logger LOG = Logger.getLogger(CDISEServiceProvider.class.getName());

    /**
     * Default provider, using ServiceLoader.
     */
    private ServiceProvider defaultServiceProvider = new PriorityAwareServiceProvider();

    @Override
    public <T> List<T> getServices(Class<T> serviceType) {
        List<T> instances = new ArrayList<T>();
        Set<String> types = new HashSet<>();
        try {
            for (T t : CDIAccessor
                    .getInstances(serviceType)) {
                instances.add(t);
                types.add(t.getClass().getName());
            }
        }catch(Exception e){
            // OK, component is not registered in CDI...
            LOG.log(Level.FINEST, "No such component in CDI context: " + serviceType.getName(), e);
        }
        for (T t : defaultServiceProvider.getServices(serviceType)) {
            if (!types.contains(t.getClass().getName())) {
                instances.add(t);
            }
        }
        instances.sort(PriorityAwareServiceProvider::compareServices);
        return instances;
    }

    public <T> List<T> getServices(Class<T> serviceType, List<T> defaultList) {
        List<T> services = getServices(serviceType);
        if (services.isEmpty()) {
            return defaultList;
        }
        return services;
    }

    @Override
    public String toString() {
        return "CDISEServiceProvider{" +
                "defaultServiceProvider=" + defaultServiceProvider +
                '}';
    }

	@Override
	public int getPriority() {
		return PRIO;
	}
}
