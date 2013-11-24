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
package org.javamoney.calc.function;

import java.util.ArrayList;
import java.util.List;




/**
 * This predicate implements the logic {@code or and xor} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} or
 * {@code OrPredicate(p1,p2) == (p1 || p2) && !(p1 && p2)}.
 * 
 * @author Anatole Tresch
 */
final class XOrPredicate<T> implements MonetaryPredicate<T> {
	/** The child predicates. */
	private List<MonetaryPredicate<? super T>> predicates = new ArrayList<MonetaryPredicate<? super T>>();

	/**
	 * Creates an XOR predicate.
	 * 
	 * @param predicates
	 *            The child predicates.
	 */
	@SafeVarargs
	XOrPredicate(Iterable<? extends MonetaryPredicate<? super T>>... predicates) {
		if (predicates != null) {
			for (Iterable<? extends MonetaryPredicate<? super T>> iterable : predicates) {
				for (MonetaryPredicate<? super T> predicate : iterable) {
					this.predicates.add(predicate);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public boolean test(T value) {
		boolean state = false;
		for (MonetaryPredicate<? super T> predicate : predicates) {
			if (predicate.test(value)) {
				if (!state) {
					state = true;
				}
				else {
					return false;
				}
			}
		}
		return state;
	}

}