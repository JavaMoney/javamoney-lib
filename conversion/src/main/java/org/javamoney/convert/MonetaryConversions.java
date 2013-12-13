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
package org.javamoney.convert;

import java.util.Collection;
import java.util.Collections;
import java.util.ServiceLoader;

import javax.money.bootstrap.Bootstrap;

import org.javamoney.convert.spi.MonetaryConversionsSingletonSpi;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney. It allows to evaluate the currently available exchange rate
 * type instances and provides access to the corresponding
 * {@link ConversionProvider} instances.
 * <p>
 * This class is thread safe.
 * <p>
 * This class is designed to support also contextual behaviour, e.g. in Java EE
 * containers each application may provide its own {@link ConversionProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link MonetaryConversionsSingletonSpi} that manages
 * the different application contexts transparently. In a SE environment this
 * class is expected to behave like an ordinary singleton, loading its SPIs from
 * the {@link ServiceLoader}.
 * <p>
 * This class is thread-safe. Hereby it is important to know that it delegates
 * to the registered {@link MonetaryConversionsSingletonSpi} SPI, which also is
 * required to be thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions {

	/**
	 * The SPI currently active, use {@link ServiceLoader} to register an
	 * alternate implementation.
	 */
	private static final MonetaryConversionsSingletonSpi MONETARY_CONVERSION_SPI = Bootstrap
			.getService(MonetaryConversionsSingletonSpi.class);

	/**
	 * Private singleton constructor.
	 */
	private MonetaryConversions() {
	}

	/**
	 * Access an instance of {@link ConversionProvider} for the given
	 * {@link ExchangeRateType}. Use {@link #getSupportedExchangeRateTypes()} to
	 * check, which {@link ConversionProvider}s are available.
	 * 
	 * @param type
	 *            the {@link ExchangeRateType} that identifies the provider
	 *            instance to be accessed, not {@code null}.
	 * 
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ConversionProvider} is available.
	 */
	public static ConversionProvider getConversionProvider(ExchangeRateType type) {
		return MONETARY_CONVERSION_SPI.getConversionProvider(type);
	}

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ConversionProvider} instances can be obtained.
	 * 
	 * @return all supported exchange rate type instances, never {@code null}.
	 */
	public static Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		return MONETARY_CONVERSION_SPI.getSupportedExchangeRateTypes();
	}

	/**
	 * Checks if a {@link ConversionProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ConversionProvider} for this exchange rate type
	 *         can be obtained from this {@link MonetaryConversions} instance.
	 */
	public static boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return MONETARY_CONVERSION_SPI.isSupportedExchangeRateType(type);
	}

}
