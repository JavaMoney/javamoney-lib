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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;


import org.javamoney.format.tokens.LiteralTokenStyleableItem;
import org.javamoney.format.tokens.NumberTokenStyleableItem;
import org.junit.Test;

public class ItemFormatBuilderTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new ItemFormatBuilder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() throws IOException {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.append(new LiteralTokenStyleableItem<Number>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.append(new NumberTokenStyleableItem(df).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.withStyle(new LocalizationContext.Builder(Number.class).build());
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testAddTokenString() throws IOException {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.append("test- ");
		b.append("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.append(new NumberTokenStyleableItem(f).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.withStyle(new LocalizationContext.Builder(Number.class).build());
		ItemFormat<Number> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testGetTokens() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.append("1");
		b.append("2");
		b.append("3");
		Collection<StyleableItemFormatToken<Double>> tokens = b.getTokens();
		int size = 0;
		for(StyleableItemFormatToken<?> token: tokens){
			assertNotNull(token);
			assertTrue(token instanceof LiteralTokenStyleableItem<?>);
			size++;
		}
		assertEquals(3, size);
	}

	@Test
	public void testGetTokenCount() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.append("1");
		b.append("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.append("1");
		b.append("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.append(new LiteralTokenStyleableItem<Number>("test "));
		b.append(new NumberTokenStyleableItem());
		b.withStyle(new LocalizationContext.Builder(Number.class).build());
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d, Locale.CHINESE));
		b.withStyle(new LocalizationContext.Builder(Number.class).build());
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d,Locale.GERMAN));
	}

}
