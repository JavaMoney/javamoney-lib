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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test currency related injection.
 */
public class CurrencyInjectionTest {

    private static CurrencyInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(CurrencyInjectedBean.class);
    }

    @Test
    public void defaultCurrency(){
        assertNotNull(bean.defaultCurrency);
    }

    @Test
    public void eurCurrency(){
        assertNotNull(bean.eurCurrency);
        assertEquals(bean.eurCurrency.getCurrencyCode(), "EUR");
    }

    @Test
    public void currencies(){
        assertNotNull(bean.currencies);
    }

    @Test
    public void historicCurrency(){
        assertNotNull(bean.historicCurrency);
    }

    @Test
    public void isoCurrencies(){
        assertNotNull(bean.isoCurrencies);
    }

    @Test
    public void numericCurrency(){
        assertNotNull(bean.numericCurrencies);
        assertTrue(bean.numericCurrencies.size()>0);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
