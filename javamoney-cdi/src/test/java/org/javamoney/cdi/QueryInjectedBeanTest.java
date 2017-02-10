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

import org.javamoney.moneta.Money;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test injection and translation of monetary queries.
 */
public class QueryInjectedBeanTest {

    private static QueryInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(QueryInjectedBean.class);
    }

    /**
     * Test conversion from annotation into query (including injection) for
     * {@code
     * @CurrencySpec(codes={"CHF", "USD"},
     * attributes = "validAt=01.01.1995",
     * countries = "IT",
     * numericCodes = {1,2,3,11},
     * providers = {"p1", "p2"})}.
     */
    @Test
    public void testCurrencyQuery(){
        assertNotNull(bean.currencyQuery);
        assertEquals(bean.currencyQuery.getCurrencyCodes().size(), 2);
        assertTrue(bean.currencyQuery.getCurrencyCodes().contains("CHF"));
        assertTrue(bean.currencyQuery.getCurrencyCodes().contains("USD"));
        assertEquals(bean.currencyQuery.get("validAt", String.class), "01.01.1995");
        assertEquals(bean.currencyQuery.getNumericCodes().size(), 4);
        assertTrue(bean.currencyQuery.getNumericCodes().contains(1));
        assertTrue(bean.currencyQuery.getNumericCodes().contains(2));
        assertTrue(bean.currencyQuery.getNumericCodes().contains(3));
        assertTrue(bean.currencyQuery.getNumericCodes().contains(11));
        assertEquals(bean.currencyQuery.getProviderNames().size(), 2);
        assertTrue(bean.currencyQuery.getProviderNames().contains("p1"));
        assertTrue(bean.currencyQuery.getProviderNames().contains("p2"));
        assertEquals(bean.currencyQuery.getCountries().size(), 1);
        assertEquals(bean.currencyQuery.getCountries().iterator().next().toString(), "_IT");
    }

    /**
     * Test conversion from annotation into query (including injection) for
     * {@code
     * @Inject @FormatSpec(name="default23",
     * attributes = "separator=-",
     * locale="DE",
     * providers = {"p1", "p2"})}.
     */
    @Test
    public void testFormatQuery(){
        assertNotNull(bean.formatQuery);
        assertEquals(bean.formatQuery.getFormatName(), "default23");
        assertEquals(bean.formatQuery.get("separator", String.class), "-");
        assertEquals(bean.formatQuery.getProviderNames().size(), 2);
        assertTrue(bean.formatQuery.getProviderNames().contains("p1"));
        assertTrue(bean.formatQuery.getProviderNames().contains("p2"));
        assertEquals(bean.formatQuery.getLocale().toString(), "de_DE");
    }

    /**
     * Test conversion from annotation into query (including injection) for
     * {@code
     * @Inject @AmountSpec(value=Money.class,
     * attributes = "foo=bar",
     * precision = 10,
     * maxScale = 2,
     * fixedScale = true,
     * providers = {"p1", "p2"})}.
     */
    @Test
    public void testAmountFactoryQuery(){
        assertNotNull(bean.amountFactoryQuery);
        assertEquals(bean.amountFactoryQuery.getTargetType(), Money.class);
        assertEquals(bean.amountFactoryQuery.get("foo", String.class), "bar");
        assertEquals(bean.amountFactoryQuery.getProviderNames().size(), 2);
        assertTrue(bean.amountFactoryQuery.getProviderNames().contains("p1"));
        assertTrue(bean.amountFactoryQuery.getProviderNames().contains("p2"));
        assertEquals(bean.amountFactoryQuery.getPrecision(), Integer.valueOf(10));
        assertEquals(bean.amountFactoryQuery.getMaxScale(), Integer.valueOf(2));
        assertTrue(bean.amountFactoryQuery.isFixedScale());
    }
}
