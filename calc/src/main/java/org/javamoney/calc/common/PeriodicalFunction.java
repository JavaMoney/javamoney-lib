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

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * Interface shared by most common functions.
 * @author Anatole Tresch
 */
public interface PeriodicalFunction {

	public <T extends MonetaryAmount> T calculate(T amount, Rate rate,
			int periods);

	public MonetaryOperator getOperator(Rate rate, int periods);

}
