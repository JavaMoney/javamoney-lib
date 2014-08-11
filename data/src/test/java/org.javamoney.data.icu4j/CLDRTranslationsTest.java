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

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests if {@link org.javamoney.data.icu4j.CLDRTranslations} returns valid data from unicode data-feed.
 * Created by atsticks on 10.08.14.
 */
public class CLDRTranslationsTest {

    @Test
    public void testGetInstance() {
        assertNotNull(CLDRTranslations.getInstance());
    }

    @Test
    public void testGetInstance_Locale() {
        assertNotNull(CLDRTranslations.getInstance(Locale.ENGLISH));
        assertNotNull(CLDRTranslations.getInstance(Locale.GERMAN));
        assertNotNull(CLDRTranslations.getInstance(Locale.FRENCH));
    }

    @Test
    public void testGetInstance_String() {
        assertNotNull(CLDRTranslations.getInstance("de"));
        assertNotNull(CLDRTranslations.getInstance("fr"));
        assertNotNull(CLDRTranslations.getInstance("en"));
    }

    @Test
    public void testCLDRTranslations_CurrencyTranslations() {
        CLDRTranslations.CurrencyTranslations translations = CLDRTranslations.getInstance(Locale.ENGLISH).getCurrencyTranslations("CHF");
        assertNotNull(translations);
        assertEquals("Swiss Franc", translations.getDisplayName());
        assertEquals("Swiss franc", translations.getDisplayNameOne());
        assertEquals("Swiss francs", translations.getDisplayNameMultiple());
        assertEquals("CHF", translations.getIsoCode());
        assertNull(translations.getSymbol());
        translations = CLDRTranslations.getInstance("de").getCurrencyTranslations("EUR");
        assertNotNull(translations);
        assertEquals("Euro", translations.getDisplayName());
        assertEquals("Euro", translations.getDisplayNameOne());
        assertEquals("Euro", translations.getDisplayNameMultiple());
        assertEquals("EUR", translations.getIsoCode());
        assertEquals("€", translations.getSymbol());
    }

    @Test
    public void testCLDRTranslations_LanguageName() {
        String lang = CLDRTranslations.getInstance(Locale.ENGLISH).getLanguageName("de");
        assertNotNull(lang);
        assertEquals("German", lang);
        lang = CLDRTranslations.getInstance("de").getLanguageName("de");
        assertNotNull(lang);
        assertEquals("Deutsch", lang);
    }

    @Test
    public void testCLDRTranslations_RegionName() {
        String region = CLDRTranslations.getInstance(Locale.ENGLISH).getRegionName("001");
        assertNotNull(region);
        assertEquals("World", region);
        region = CLDRTranslations.getInstance("de").getRegionName("003");
        assertNotNull(region);
        assertEquals("Nordamerika", region);
    }
}
