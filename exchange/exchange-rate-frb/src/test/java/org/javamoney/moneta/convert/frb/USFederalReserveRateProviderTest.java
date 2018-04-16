/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
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
 * Contributors: @atsticks, @keilw, @otjava
 */
package org.javamoney.moneta.convert.frb;

import static javax.money.convert.MonetaryConversions.getExchangeRateProvider;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;

import org.javamoney.moneta.Money;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class USFederalReserveRateProviderTest {
    private static final CurrencyUnit EURO = Monetary.getCurrency("EUR");
    private static final CurrencyUnit CANADA_DOLLAR = Monetary.getCurrency("CAD");
    private static final CurrencyUnit DOLLAR = Monetary.getCurrency("USD");
    private static final CurrencyUnit TAIWAN_DOLLAR = Monetary.getCurrency("TWD");

    private ExchangeRateProvider provider;

    @BeforeTest
    public void setup() throws InterruptedException {
        provider = getExchangeRateProvider(USFederalReserveExchangeRateType.FRB);
        Thread.sleep(20_000L);
    }

    @Test
    public void shouldReturnUSFederalReserverRateProvider() {
        assertTrue(Objects.nonNull(provider));
        assertEquals(provider.getClass(), USFederalReserveRateProvider.class);
    }

    @Test
    public void shouldReturnsSameDollarValue() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), DOLLAR);
        assertEquals(result.getNumber().numberValue(BigDecimal.class), BigDecimal.TEN);

    }

    @Test
    public void shouldReturnsSameTawainValue() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(TAIWAN_DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, TAIWAN_DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), TAIWAN_DOLLAR);
        assertEquals(result.getNumber().numberValue(BigDecimal.class), BigDecimal.TEN);

    }

    @Test
    public void shouldReturnsSameEuroValue() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(EURO);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, EURO);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), EURO);
        assertEquals(result.getNumber().numberValue(BigDecimal.class), BigDecimal.TEN);

    }

    @Test
    public void shouldConvertsDollarToEuro() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(EURO);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), EURO);
        assertTrue(result.getNumber().doubleValue() > 0);
    }

    @Test
    public void shouldConvertsEuroToDollar() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, EURO);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), DOLLAR);
        assertTrue(result.getNumber().doubleValue() > 0);
    }

    @Test
    public void shouldConvertsDollarToCanada() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(CANADA_DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), CANADA_DOLLAR);
        assertTrue(result.getNumber().doubleValue() > 0);
    }

    @Test
    public void shouldConvertCanadaToDollar() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, CANADA_DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), DOLLAR);
        assertTrue(result.getNumber().doubleValue() > 0);
    }

    @Test
    public void shouldConvertTaiwanToEuro() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(EURO);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, TAIWAN_DOLLAR);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), EURO);
        assertTrue(result.getNumber().doubleValue() > 0);

    }

    @Test
    public void shouldConvertsEuroToTawain() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(TAIWAN_DOLLAR);
        assertNotNull(currencyConversion);
        MonetaryAmount money = Money.of(BigDecimal.TEN, EURO);
        MonetaryAmount result = currencyConversion.apply(money);

        assertEquals(result.getCurrency(), TAIWAN_DOLLAR);
        assertTrue(result.getNumber().doubleValue() > 0);
    }

    @Test
    public void shouldHaveExchangeRates() {
        CurrencyConversion currencyConversion = provider.getCurrencyConversion(DOLLAR);
        assertNotNull(currencyConversion);
        int count = 0;
        for (Currency currency : Currency.getAvailableCurrencies()) {
            MonetaryAmount money = Money.of(BigDecimal.ONE, currency.getCurrencyCode());
            try {
                MonetaryAmount result = currencyConversion.apply(money);
                assertTrue(result.getNumber().doubleValue() > 0);
                count++;
            } catch(Exception e) {
                //not a supported currency
            }
        }
        assertTrue(count >=24);
    }


}
