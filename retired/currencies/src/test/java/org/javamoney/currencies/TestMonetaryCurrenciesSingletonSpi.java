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

///**
// * Empty pseudo implementation for testing only.
// * 
// * @author Anatole Tresch
// * 
// */
//public class TestMonetaryCurrenciesSingletonSpi implements
//		MonetaryCurrenciesSingletonSpi {
//
//
//	@Override
//	public boolean isAvailable(String currencyCode) {
//		return TestCurrency.of(currencyCode) != null;
//	}
//
//	@Override
//	public CurrencyUnit get(String currencyCode) {
//		CurrencyUnit unit = TestCurrency.of(currencyCode);
//		if (unit != null) {
//			return unit;
//		}
//		throw new IllegalArgumentException(currencyCode);
//	}
//
//	@Override
//	public boolean isNamespaceAvailable(String namespace) {
//		throw new UnsupportedOperationException("Not supported yet."); // To
//																		// change
//																		// body
//																		// of
//																		// generated
//																		// methods,
//																		// choose
//																		// Tools
//																		// |
//																		// Templates.
//	}
//
//	@Override
//	public Collection<String> getNamespaces() {
//		throw new UnsupportedOperationException("Not supported yet."); // To
//																		// change
//																		// body
//																		// of
//																		// generated
//																		// methods,
//																		// choose
//																		// Tools
//																		// |
//																		// Templates.
//	}
//
//
//	@Override
//	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
//			long timestamp) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public Collection<CurrencyUnit> getAll(String namespace) {
//		return TestCurrency.getAllMatching(namespace);
//	}
//
//	@Override
//	public String getNamespace(String code) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//}
