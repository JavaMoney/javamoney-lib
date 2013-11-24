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
package org.javamoney.calc.function;

import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;

import org.javamoney.calc.FlavoredMonetaryAmount;


final class MonetaryAmountFlavorPredicate<T extends MonetaryAmount> implements
		MonetaryPredicate<T> {

	private Set<String> flavors = new HashSet<String>();

	public MonetaryAmountFlavorPredicate(String... flavors) {
		if (flavors != null) {
			for (String flavor : flavors) {
				this.flavors.add(flavor);
			}
		}
	}

	public MonetaryAmountFlavorPredicate(Iterable<String>... flavors) {
		if (flavors != null) {
			for (Iterable<String> flavorIter : flavors) {
				for (String flavor : flavorIter) {
					this.flavors.add(flavor);
				}
			}
		}
	}

	@Override
	public boolean test(T value) {
		if (!(value instanceof FlavoredMonetaryAmount)) {
			return Boolean.FALSE;
		}
		return this.flavors.contains(((FlavoredMonetaryAmount) value)
				.getAmountFlavor());
	}
}
