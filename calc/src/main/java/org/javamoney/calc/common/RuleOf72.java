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

import org.javamoney.calc.CalculationContext;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Rule%20of%2072%201.gif"/> <br/>
 * The Rule of 72 is a simple formula used to estimate the length of time required to double an
 * investment. The rule of 72 is primarily used in off the cuff situations where an individual needs
 * to make a quick calculation instead of working out the exact time it takes to double an
 * investment. Also, one is more likely to remember the rule of 72 than the exact formula for
 * doubling time or may not have access to a calculator that allows logarithms.
 * </p>
 * <h2>Example of Rule of 72</h2>
 * <p>
 * An individual is earning 6% on their money market account would like to estimate how long it
 * would take to double their current balance. In order for this estimation to be remotely accurate,
 * we must assume that there will be no withdrawals nor deposits into this account. We can estimate
 * that it will take approximately 12 years to double the current balance.
 * </p>
 * 
 * @see http://www.financeformulas.net/Rule_of_72.html
 * @author Anatole Tresch
 */
public final class RuleOf72 {

	private static final BigDecimal BD72 = BigDecimal.valueOf(72);
	
	private RuleOf72() {
	}

    public static BigDecimal calculate(Rate input) {
        return BD72.divide(input.get().multiply(BigDecimal.valueOf(100)),
				CalculationContext.mathContext());
	}
}
