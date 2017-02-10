/*
 * Copyright (c) 2012-2017 Anatole Tresch.
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
package org.javamoney.cdi;

import org.javamoney.cdi.api.CurrencySpec;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import java.util.Collection;

/**
 * Test bean for injection of currency related beans and providers.
 */
@Dependent
public class CurrencyInjectedBean {

    @Inject
    CurrencyUnit defaultCurrency;

    @Inject @CurrencySpec(codes="EUR")
    CurrencyUnit eurCurrency;

    @Inject @CurrencySpec(
            attributes = "validAt=01.01.1995",
            countries = "IT")
    CurrencyUnit historicCurrency;

    @Inject @CurrencySpec(numericCodes = 11)
    Collection<CurrencyUnit> numericCurrencies;

    @Inject @CurrencySpec(providers = "ISO")
    Collection<CurrencyUnit> isoCurrencies;

    @Inject @CurrencySpec(codes={"CHF", "USD"})
    Collection<CurrencyUnit> currencies;

}
