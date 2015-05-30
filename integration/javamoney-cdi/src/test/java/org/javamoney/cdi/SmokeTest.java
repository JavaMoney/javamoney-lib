/*
 * Copyright (c) 2012, 2015, Werner Keil, Credit Suisse (Anatole Tresch).
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;
import javax.money.convert.*;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class SmokeTest {
    private static final Logger logger = LoggerFactory.getLogger(SmokeTest.class);

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
    }

    @Test
    public void testCreateAmounts() {
        // Creating one
        CurrencyUnit currency = Monetary.getCurrency("CHF");
        Money amount1 = Money.of(1.0d, currency);
        Money amount2 = Money.of(1.0d, currency);
        Money amount3 = amount1.add(amount2);
        logger.debug(amount1 + " + " + amount2 + " = " + amount3);
        assertEquals(1.0d, amount1.getNumber().doubleValue(), 0);
        assertEquals(1.0d, amount2.getNumber().doubleValue(), 0);
        assertEquals(2.0d, amount3.getNumber().doubleValue(), 0);
    }

    @Test
    public void testCreateMoney() {
        // Creating one
        Money amount1 = Money.of(1.0d, "CHF");
        Money amount2 = Money.of(1.0d, "CHF");
        Money amount3 = amount1.add(amount2);
        logger.debug(amount1 + " + " + amount2 + " = " + amount3);
        assertEquals(1.0d, amount1.getNumber().doubleValue(), 0);
        assertEquals(1.0d, amount2.getNumber().doubleValue(), 0);
        assertEquals(2.0d, amount3.getNumber().doubleValue(), 0);
    }

    @Test
    public void testExchange() {
        ExchangeRateProvider prov = MonetaryConversions.getExchangeRateProvider("ECB");
        assertNotNull(prov);
        ExchangeRate rate1 =
                prov.getExchangeRate(Monetary.getCurrency("CHF"), Monetary.getCurrency("EUR"));
        ExchangeRate rate2 =
                prov.getExchangeRate(Monetary.getCurrency("EUR"), Monetary.getCurrency("CHF"));
        ExchangeRate rate3 =
                prov.getExchangeRate(Monetary.getCurrency("CHF"), Monetary.getCurrency("USD"));
        ExchangeRate rate4 =
                prov.getExchangeRate(Monetary.getCurrency("USD"), Monetary.getCurrency("CHF"));
        System.out.println(rate1);
        System.out.println(rate2);
        System.out.println(rate3);
        System.out.println(rate4);
    }


    @Test
    public void testAmountFormatRoundTrip() throws ParseException {
        // Using parsers
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        assertNotNull(format);
        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("CHF").setNumber(10.50).create();
        String formatted = format.format(amount);
        assertNotNull(formatted);
        MonetaryAmount parsed = format.parse(formatted);
        assertNotNull(parsed);
        assertEquals(amount, parsed);
    }

    @Test
    public void testCurrencyAccess() {
        // Creating one
        CurrencyUnit currency = Monetary.getCurrency("INR");
        assertNotNull(currency);
        currency = Monetary.getCurrency("CDITest");
        assertNotNull(currency);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
