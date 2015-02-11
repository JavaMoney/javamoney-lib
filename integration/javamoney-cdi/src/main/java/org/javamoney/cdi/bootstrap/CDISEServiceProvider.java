/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 */
package org.javamoney.cdi.bootstrap;

import java.util.*;

import javax.enterprise.inject.Instance;
import javax.money.spi.ServiceProvider;

import org.javamoney.moneta.internal.PriorityAwareServiceProvider;
import org.javamoney.moneta.spi.ServicePriority;

/**
 * Overriding ServiceProvider that actually tries to satisfy component requests from CDI,
 * where possible. Additionally ServiceLoader based service are loaded and are returned
 * ONLY, when not the same service is loaded as well in CDI.
 */
public class CDISEServiceProvider implements ServiceProvider {
    /**
     * Default provider, using ServiceLoader.
     */
    private ServiceProvider defaultServiceProvider = new PriorityAwareServiceProvider();

    /**
     * Get the components priority, which returns 100.
     *
     * @return 100.
     */
    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public <T> List<T> getServices(Class<T> serviceType) {
        List<T> instances = new ArrayList<T>();
        Set<String> types = new HashSet<>();
        for (T t : CDIAccessor
                .getInstances(serviceType)) {
            instances.add(t);
            types.add(t.getClass().getName());
        }
        for (T t : defaultServiceProvider.getServices(serviceType)) {
            if (!types.contains(t.getClass().getName())) {
                instances.add(t);
            }
        }
        instances.sort(PriorityAwareServiceProvider::compareServices);
        return instances;
    }

    @Override
    public String toString() {
        return "CDISEServiceProvider{" +
                "defaultServiceProvider=" + defaultServiceProvider +
                '}';
    }
}
