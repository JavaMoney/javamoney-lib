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
package org.javamoney.cdi.internal;

import javax.money.spi.ServiceProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default SE ServiceLoader based ServiceProvider implementation.
 */
public class DefaultServiceProvider implements ServiceProvider {
    /**
     * List of services loaded, per class.
     */
    private final ConcurrentHashMap<Class, List<Object>> servicesLoaded = new ConcurrentHashMap<>();

    /**
     * Loads and registers services.
     *
     * @param serviceType The service type.
     * @param <T>         the concrete type.
     * @param defaultList the list of items returned, if no services were found.
     * @return the items found, never {@code null}.
     */
    public <T> List<T> getServices(final Class<T> serviceType, final List<T> defaultList) {
        @SuppressWarnings("unchecked")
        List<T> found = (List<T>) servicesLoaded.get(serviceType);
        if (found != null) {
            return found;
        }

        return loadServices(serviceType, defaultList);
    }

    /**
     * Loads and registers services.
     *
     * @param serviceType The service type.
     * @param <T>         the concrete type.
     * @param defaultList the list of items returned, if no services were found.
     * @return the items found, never {@code null}.
     */
    private <T> List<T> loadServices(final Class<T> serviceType, final List<T> defaultList) {
        try {
            List<T> services = new ArrayList<>();
            for (T t : ServiceLoader.load(serviceType)) {
                services.add(t);
            }
            if (services.isEmpty()) {
                services.addAll(defaultList);
            }
            @SuppressWarnings("unchecked")
            final List<T> previousServices = (List<T>) servicesLoaded.putIfAbsent(serviceType, (List<Object>) services);
            return Collections.unmodifiableList(previousServices != null ? previousServices : services);
        } catch (Exception e) {
            Logger.getLogger(DefaultServiceProvider.class.getName()).log(Level.WARNING,
                    "Error loading services of type " + serviceType, e);
            return defaultList;
        }
    }

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public <T> List<T> getServices(Class<T> serviceType) {
		// TODO Auto-generated method stub
		return null;
	}

}