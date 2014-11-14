/*
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil.
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
package org.javamoney.currencies.spi;

import javax.money.CurrencyUnit;
import javax.money.spi.Bootstrap;

/**
 * This class models mappings for {@link CurrencyUnit} instances. It is used by
 * the {@link CurrencyMappingsSingletonSpi} implementation.<br>
 * It is the responsibility of the {@link Bootstrap} loader to load the and
 * manage the instances of {@link CurrencyUnitMapperSpi}.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyUnitMapperSpi {

    /**
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param currencyUnit    The source unit, never {@code null}.
     * @param targetNamespace the target currency namespace, never {@code null}.
     * @param timestamp       the target timestamp, may be {@code null}.
     * @return The mapped {@link CurrencyUnit}, or {@code null}.
     */
    public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
                            Long timestamp);

}
