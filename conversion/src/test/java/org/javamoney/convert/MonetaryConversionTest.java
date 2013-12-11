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
package org.javamoney.convert;

import static org.junit.Assert.*;

import java.util.Collection;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;
import org.javamoney.convert.TestMonetaryConversionSpi.DummyConversionProvider;
import org.javamoney.convert.provider.CompoundConversionProvider;
import org.junit.Ignore;
import org.junit.Test;

public class MonetaryConversionTest {

	@Test
	public void testGetConversionProvider() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(ExchangeRateType.of("EZB"));
		assertTrue(prov != null);
		assertEquals(CompoundConversionProvider.class, prov.getClass());
	}

	@Test
	public void testGetSupportedExchangeRateTypes() {
		Collection<ExchangeRateType> types = MonetaryConversions
			.getSupportedExchangeRateTypes();
		assertNotNull(types);
		assertTrue(types.size() >= 1);
		assertTrue(types.contains(ExchangeRateType.of("IMF")));
		assertTrue(types.contains(ExchangeRateType.of("EZB")));
	}

	@Test
	public void testIsSupportedExchangeRateType() {
		assertTrue(MonetaryConversions
				.isSupportedExchangeRateType(ExchangeRateType.of("IMF")));
		assertFalse(MonetaryConversions
				.isSupportedExchangeRateType(ExchangeRateType.of("foo")));
	}

}
