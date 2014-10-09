package org.javamoney.cdi.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.enterprise.inject.Instance;
import javax.money.spi.ServiceProvider;

import org.javamoney.cdi.CDIContainer;
import org.javamoney.moneta.spi.ServicePriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServicePriority(ServicePriority.NORM_PRIORITY + 1)
public class CDIServices implements ServiceProvider {


    @Override
    public <T> List<T> getServices(Class<T> serviceType) {
        List<T> instances = new ArrayList<T>();
        for (T t : CDIContainer
                .getInstances(serviceType)) {
            instances.add(t);
        }
        return instances;
    }

    @Override
    public <T> List<T> getServices(Class<T> serviceType,
                                   List<T> defaultList) {
        Instance<T> found = CDIContainer
                .getInstances(serviceType);
        List<T> instances = new ArrayList<T>();
        for (T t : found) {
            instances.add(t);
        }
        try {
            for (T t : ServiceLoader.load(serviceType)) {
                instances.add(t);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).warn("Error loading " + serviceType.getName() + " from ServiceLoader.", e);
        }
        if (instances.isEmpty()) {
            return defaultList;
        }
        return instances;
    }


}
