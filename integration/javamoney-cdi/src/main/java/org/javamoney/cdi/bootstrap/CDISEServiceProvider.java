package org.javamoney.cdi.bootstrap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Instance;
import javax.money.spi.DefaultServiceProvider;
import javax.money.spi.ServiceProvider;

import org.javamoney.moneta.spi.ServicePriority;
import org.slf4j.LoggerFactory;

/**
 * Overriding ServiceProvider that actually tries to satisfy component requests from CDI,
 * where possible. Additionally ServiceLoader based service are loaded and are returned
 * ONLY, when not the same service is loaded as well in CDI.
 */
@ServicePriority(ServicePriority.NORM_PRIORITY + 1)
public class CDISEServiceProvider implements ServiceProvider {
    /**
     * Default provider, using ServiceLoader.
     */
    private ServiceProvider defaultServiceProvider = new DefaultServiceProvider();

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
        return instances;
    }

    @Override
    public <T> List<T> getServices(Class<T> serviceType,
                                   List<T> defaultList) {
        Instance<T> found = CDIAccessor
                .getInstances(serviceType);
        Set<String> types = new HashSet<>();
        List<T> instances = new ArrayList<T>();
        for (T t : found) {
            instances.add(t);
            types.add(t.getClass().getName());
        }
        for (T t : defaultServiceProvider.getServices(serviceType)) {
            if (!types.contains(t.getClass().getName())){
                instances.add(t);
            }
        }
        if (instances.isEmpty()) {
            return defaultList;
        }
        return instances;
    }

    @Override
    public String toString() {
        return "CDISEServiceProvider{" +
                "defaultServiceProvider=" + defaultServiceProvider +
                '}';
    }
}
