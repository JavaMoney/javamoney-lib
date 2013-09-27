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
package org.javamoney.currencies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.javamoney.currencies.MonetaryCurrencies;
import org.junit.Test;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class MonetaryCurrenciesTest {

	@Test
	public void testGetString() {
		assertEquals("CHF", MonetaryCurrencies.get("CHF").getCurrencyCode());
	}

	@Test
	public void testGetStringString() {
		MonetaryCurrencies.get("CHF");
	}

	@Test
	public void testGetAllIso() {
		assertNotNull(MonetaryCurrencies.getAll("ISO-4217"));
	}
}
