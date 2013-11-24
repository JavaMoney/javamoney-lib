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

import java.util.Collection;

import javax.money.MonetaryAmount;



public class MonetaryCalculator {

	public String addFilter(MonetaryPredicate<MonetaryAmount> filter) {
		return filter.getClass().getSimpleName();
	}

	public String addFilter(String filterId,
			MonetaryPredicate<MonetaryAmount> filter) {
		return filterId;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getFilter(
			String filterId) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T removeFilter(
			String filterId) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getFilter(
			Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getFilter(
			String visitorId, Class<T> visitorType) {
		return null;
	}

	public String addAmountVisitor(
			MonetaryPredicate<MonetaryAmount> visitor) {
		return visitor.getClass().getSimpleName();
	}

	public String addAmountVisitor(String visitorId,
			MonetaryPredicate<MonetaryAmount> visitor) {
		return visitorId;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getVisitor(
			String visitorId) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getVisitor(
			Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T getVisitor(
			String visitorId, Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryPredicate<MonetaryAmount>> T removeVisitor(
			String visitorId) {
		return null;
	}

	public Collection<String> getVisitorIds() {
		return null;
	}

	public Collection<String> getFilterIds() {
		return null;
	}

	public Collection<MonetaryAmount> getFiltered(String filterId) {
		return null;
	}

	public int getVisited(String visitorId) {
		return 0;
	}

	public void init(MonetaryAmount... amounts) {

	}

	public void init(Collection<MonetaryAmount> amounts) {

	}

	public void reset() {

	}

	public void calculate() {

	}

}
