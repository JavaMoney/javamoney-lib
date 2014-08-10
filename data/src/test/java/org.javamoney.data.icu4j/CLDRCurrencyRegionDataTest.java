/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.data.icu4j;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests if {@link org.javamoney.data.icu4j.CLDRCurrencyRegionData} returns valid data from unicode data-feed.
 * Created by atsticks on 10.08.14.
 */
public class CLDRCurrencyRegionDataTest {

    @Test
    public void testGetInstance() {
        assertNotNull(CLDRCurrencyRegionData.getInstance());
    }

    @Test
    public void testGetCurrencyData() {
        CLDRCurrencyRegionData.Currency4Region regionData = CLDRCurrencyRegionData.getInstance().getCurrencyData("DE");
        assertNotNull(regionData);
        assertEquals("DE", regionData.getRegionCode());
        List<CLDRCurrencyRegionData.CurrencyRegionRecord> data = regionData.getEntries();
        assertNotNull(data);
        assertFalse(data.isEmpty());
        for (CLDRCurrencyRegionData.CurrencyRegionRecord en : data) {
            assertNotNull(en.getRegionCode());
            assertNotNull(en.getFromYMD());
            assertNotNull(en.getCurrencyCode());
        }
    }
}
