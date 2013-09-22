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
package org.javamoney.extras.filter;

import java.util.Set;

import javax.money.MonetaryAmount;

import org.javamoney.extras.FlavoredMonetaryAmount;
import org.javamoney.extras.MonetaryAmountFlavor;


//public class MonetaryAmountFlavorPredicate extends
//		InstancesPredicate<MonetaryAmountFlavor> {
//
//	protected boolean isPredicateTrue(MonetaryAmount value,
//			Set<MonetaryAmountFlavor> acceptedValues) {
//		if (value instanceof FlavoredMonetaryAmount) {
//			return acceptedValues.contains(((FlavoredMonetaryAmount) value)
//					.getAmountFlavor());
//		}
//		return false;
//	}
//
//}
