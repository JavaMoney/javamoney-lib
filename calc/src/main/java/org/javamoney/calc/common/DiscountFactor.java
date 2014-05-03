/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.common;

import java.math.BigDecimal;

/**
 * 
 * @author Anatole
 * @author Werner
 * 
 */
public final class DiscountFactor {

	private static final DiscountFactor INSTANCE = new DiscountFactor();

	private DiscountFactor() {
	}

	public static final DiscountFactor getInstance() {
		return INSTANCE;
	}

	public BigDecimal calculate(Rate rate, int periods) {
		// (1-(1+r)^n)/1-(1+rate)
		BigDecimal div = BigDecimal.ONE
				.min(BigDecimal.ONE.add(rate.get()));
		BigDecimal factor = BigDecimal.ONE.subtract(
				BigDecimal.ONE.add(rate.get()).pow(periods)).divide(div);
		return BigDecimal.ONE.add(factor);
	}

}
