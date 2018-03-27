/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
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
 *
 * Contributors: @atsticks, @keilw, @otjava
 */
package org.javamoney.moneta.internal.convert.yahoo;

import javax.money.convert.ProviderContext;
import javax.money.convert.ProviderContextBuilder;
import javax.money.convert.RateType;

public class YahooRateProvider extends YahooAbstractRateProvider {

	public static final String PROVIDER = "YAHOO";


	private static final String DATA_ID = YahooRateProvider.class
			.getSimpleName();

	private static final ProviderContext CONTEXT = ProviderContextBuilder
			.of(PROVIDER, RateType.DEFERRED)
			.set("providerDescription", "Yahoo currency rates").set("days", 1)
			.build();

	public YahooRateProvider() {
		super(CONTEXT);
	}

	@Override
	protected String getDataId() {
		return DATA_ID;
	}

}