/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
package org.javamoney.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Locale;

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.TokenizableFormats;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests class for {@link TokenizableFormats}.
 * 
 * @author Anatole Tresch
 * 
 */
public class MonetaryFormatsTest {

	/**
	 * Test method for
	 * {@link org.javamoney.format.TokenizableFormats#getSupportedStyleIds(java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetSupportedStyleIds() {
		Collection<String> ids = TokenizableFormats
				.getSupportedStyleIds(String.class);
		assertNotNull(ids);
		assertTrue(ids.size() == 0);
		//assertTrue(ids.contains("String"));
	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.TokenizableFormats#isSupportedStyle(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public void testIsSupportedStyle() {
		assertTrue(TokenizableFormats.isSupportedStyle(String.class, "String"));
		assertTrue(TokenizableFormats.isSupportedStyle(Integer.class, "Integer"));
		assertFalse(TokenizableFormats.isSupportedStyle(Integer.class, "String"));
		assertFalse(TokenizableFormats.isSupportedStyle(Double.class, "Integer"));

	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.TokenizableFormats#getItemFormat(java.lang.Class, org.javamoney.format.LocalizationStyle)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test(expected = ItemFormatException.class)
	public void testGetItemFormatterClassOfTLocalizationStyle()
			throws ItemParseException {
		ItemFormat<String> formatter = TokenizableFormats.getItemFormat(
				String.class, LocalizationStyle.of(String.class));
		assertEquals("testest", formatter.format("testest", Locale.ENGLISH));
		assertEquals("gugus", formatter.format("gugus", Locale.ENGLISH));
		assertEquals(LocalizationStyle.of(String.class), formatter.getStyle());
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest", Locale.ENGLISH));
		assertEquals("", formatter.parse("gugus", Locale.ENGLISH));
	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.TokenizableFormats#getItemFormat(java.lang.Class, java.util.Locale)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test
	@Ignore
	public void testGetItemFormatterClassOfTLocale() throws ItemParseException {
		ItemFormat<String> formatter = TokenizableFormats.getItemFormat(
				String.class, LocalizationStyle.of(String.class));
		assertEquals("testest", formatter.format("testest", Locale.ENGLISH));
		assertEquals("gugus", formatter.format("gugus", Locale.ENGLISH));
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest", Locale.ENGLISH));
		assertEquals("", formatter.parse("gugus", Locale.ENGLISH));
	}

}
