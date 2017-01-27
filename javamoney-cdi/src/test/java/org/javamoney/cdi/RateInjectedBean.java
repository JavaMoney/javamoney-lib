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
package org.javamoney.cdi;

import org.javamoney.cdi.api.ConversionSpec;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.RateType;
import java.util.Collection;
import java.util.List;

/**
 * Test bean for injection of rate related beans and providers.
 */
@RequestScoped
public class RateInjectedBean {

    @Inject @ConversionSpec(rateTypes = RateType.DEFERRED)
    ExchangeRateProvider deferredProvider;

    @Inject @ConversionSpec(providers = "ECB")
    ExchangeRateProvider ecbProvider;

    @Inject @ConversionSpec(termCurrency = "EUR")
    CurrencyConversion euroConverter;

    @Inject
    Collection<ExchangeRateProvider> allProviders;

    @Inject @ConversionSpec(providers = "ECB")
    Collection<ExchangeRateProvider> ecbProviderAsList;

    @Inject @ConversionSpec(rateTypes = RateType.HISTORIC)
    Collection<ExchangeRateProvider> allHistoricProviders;

    @Inject @ConversionSpec(baseCurrency = "CHF", termCurrency = "EUR",
    rateTypes = RateType.DEFERRED)
    ExchangeRate chfEurRate;

    @Inject @ConversionSpec(baseCurrency = "CHF", termCurrency = "EUR")
    Collection<ExchangeRate> allChfEurRates;

}
