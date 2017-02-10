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

import org.javamoney.cdi.api.AmountSpec;
import org.javamoney.cdi.api.CurrencySpec;
import org.javamoney.cdi.api.FormatSpec;
import org.javamoney.moneta.Money;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.money.CurrencyQuery;
import javax.money.MonetaryAmountFactoryQuery;
import javax.money.format.AmountFormatQuery;

/**
 * Test bean for injection of currency related beans and providers.
 */
@Dependent
public class QueryInjectedBean {

    @Inject @CurrencySpec(codes={"CHF", "USD"},
            attributes = "validAt=01.01.1995",
            countries = "IT",
            numericCodes = {1,2,3,11},
            providers = {"p1", "p2"})
    CurrencyQuery currencyQuery;

    @Inject @FormatSpec(name="default23",
            attributes = "separator=-",
            locale="de_DE",
            providers = {"p1", "p2"})
    AmountFormatQuery formatQuery;

    @Inject @AmountSpec(value=Money.class,
            attributes = "foo=bar",
            precision = 10,
            maxScale = 2,
            fixedScale = true,
            providers = {"p1", "p2"})
    MonetaryAmountFactoryQuery amountFactoryQuery;

}
