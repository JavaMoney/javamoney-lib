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

import static org.junit.Assert.assertNotNull;

/**
 * Tests injection of amounts and amount factories.
 */
public class AmountInjectionTest {

    private static AmountInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(AmountInjectedBean.class);
    }

    @Test
    public void testFixedScaleFactories(){
        assertNotNull(bean.fixedScaleFactories);
    }

    @Test
    public void testMoneyFactory(){
        assertNotNull(bean.moneyFactory);
    }

    @Test
    public void testPrecisionFactory(){
        assertNotNull(bean.precisionFactory);
    }

    @Test
    public void testProviderFactories(){
        assertNotNull(bean.providerFactories);
    }

    @Test
    public void testScaledFactory(){
        assertNotNull(bean.scaledFactory);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
