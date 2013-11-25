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

import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

public class Composition implements MonetaryOperator {

	private List<MonetaryOperator> functions = new ArrayList<>();

	@SafeVarargs
	public Composition(Iterable<MonetaryOperator>... operations) {
		if (operations != null) {
			for (Iterable<MonetaryOperator> iterable : operations) {
				for (MonetaryOperator monetaryOperator : iterable) {
					functions.add(monetaryOperator);
				}
			}
		}
	}

	public Composition(MonetaryOperator... operations) {
		for (MonetaryOperator monetaryOperator : operations) {
			functions.add(monetaryOperator);
		}
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		MonetaryAmount amount = value;
		for (MonetaryOperator op : functions) {
			amount = op.apply(amount);
		}
		return amount;
	}

}
