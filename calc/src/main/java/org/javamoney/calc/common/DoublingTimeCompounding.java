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
package org.javamoney.calc.common;

import java.math.BigDecimal;

import javax.money.MonetaryAdjuster;

import org.javamoney.function.MonetaryFunction;



/**
 * The formula for doubling time with continuous compounding is used to
 * calculate the length of time it takes doubles one's money in an account or
 * investment that has continuous compounding. It is important to note that this
 * formula will return a time to double based on the term of the rate. For
 * example, if the monthly rate is used, the answer to the formula will return
 * the number of months it takes to double. If the annual rate is used, the
 * answer will then reflect the number of years to double.
 * 
 * @see http://www.financeformulas.net/Doubling-Time-Continuous-Compounding.html
 * @author Anatole Tresch
 */
public class DoublingTimeCompounding implements MonetaryFunction<Rate, BigDecimal> {

	/**
	 * This function returnes the number of periods required to double an amount
	 * with continous compounding, given a rate.
	 */
	@Override
	public BigDecimal calculate(Rate rate) {
		return BigDecimal.valueOf(Math.log(2.0d)).divide(rate.getRate());
	}

}
