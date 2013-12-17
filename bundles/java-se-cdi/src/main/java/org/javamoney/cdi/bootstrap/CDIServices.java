package org.javamoney.cdi.bootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.money.spi.ServiceProvider;

import org.javamoney.cdi.CDIContainer;
import org.slf4j.LoggerFactory;

public class CDIServices implements ServiceProvider {

	@Override
	public <T> T getService(Class<T> serviceType) {
		return CDIContainer
				.getInstance(serviceType);
	}

	@Override
	public <T> T getService(Class<T> serviceType, T defaultInstance) {
		try {
			return CDIContainer
					.getInstance(serviceType);
		} catch (Exception e) {
			LoggerFactory.getLogger(CDIServices.class).debug(
					"Component not found: " + serviceType.getName()
							+ ", returning default: " + defaultInstance);
			return defaultInstance;
		}
	}

	@Override
	public <T> Collection<T> getServices(Class<T> serviceType) {
		List<T> instances = new ArrayList<T>();
		for (T t : CDIContainer
				.getInstances(serviceType)) {
			instances.add(t);
		}
		return instances;
	}

	@Override
	public <T> Collection<T> getServices(Class<T> serviceType,
			Collection<T> defaultList) {
		Instance<T> found = CDIContainer
				.getInstances(serviceType);
		if (found.isUnsatisfied()) {
			return defaultList;
		}
		List<T> instances = new ArrayList<T>();
		for (T t : found) {
			instances.add(t);
		}
		return instances;
	}

	@Override
	public int getPriority() {
		return 10;
	}

}
