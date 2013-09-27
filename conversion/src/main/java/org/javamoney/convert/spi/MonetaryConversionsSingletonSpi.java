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
package org.javamoney.convert.spi;

import java.util.Collection;
import java.util.ServiceLoader;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;

/**
 * This is the SPI to be implemented, and that is registered into the
 * {@code MonetaryConversions} singleton accessor. It should be registered as a
 * service using the JDK {@code ServiceLoader}. Hereby only one instance can be
 * registered at a time.<br/>
 * This interface is designed to support also contextual behaviour, e.g. in Java
 * EE containers each application may provide its own {@link ConversionProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link MonetaryConversionsSingletonSpi} that manages
 * the different application contexts transparently. In a SE environment this
 * class is expected to behave like an ordinary singleton, loading its SPIs from
 * the {@link ServiceLoader}.
 * <p>
 * Instances of this class must be thread safe. It is not a requirement that
 * they are serializable.
 * <p>
 * Only one instance can be registered using the {@link ServiceLoader}. When
 * registering multiple instances the {@link MonetaryConversions} accessor will
 * not work.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryConversionsSingletonSpi {

	/**
	 * Access an instance of {@link ConversionProvider}.
	 * 
	 * @param type
	 *            The rate type.
	 * @return the provider, if it is a registered rate type, never null.
	 * @see #isSupportedExchangeRateType(ExchangeRateType)
	 */
	ConversionProvider getConversionProvider(ExchangeRateType type);

	/**
	 * Get all currently registered {@link ExchangeRateType} instances.
	 * 
	 * @return all currently registered rate types
	 */
	Collection<ExchangeRateType> getSupportedExchangeRateTypes();

	/**
	 * Allows to quickly check, if a {@link ExchangeRateType} is supported.
	 * 
	 * @param type
	 *            the exchange rate type
	 * @return {@code true}, if the rate is supported, meaning an according
	 *         {@link ConversionProvider} can be loaded.
	 * @see #getConversionProvider(ExchangeRateType)
	 */
	boolean isSupportedExchangeRateType(ExchangeRateType type);
}
