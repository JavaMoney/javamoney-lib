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

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.calc.function.CompoundCalculation;
import org.javamoney.calc.function.CompoundType;
import org.javamoney.calc.function.CompoundValue;
import org.javamoney.moneta.Money;

/**
 * Present Value (PV) is a formula used in Finance that calculates the present day value of an
 * amount that is received at a future date. The premise of the equation is that there is
 * "time value of money".
 * <p>
 * Time value of money is the concept that receiving something today is worth more than receiving
 * the same item at a future date. The presumption is that it is preferable to receive $100 today
 * than it is to receive the same amount one year from today, but what if the choice is between $100
 * present day or $106 a year from today? A formula is needed to provide a quantifiable comparison
 * between an amount today and an amount at a future time, in terms of its present day value.
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Present%20Value%201.gif" />
 * <p>
 * alterantively this can be written also as (which is much easier to implement):<br/>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Present%20Value%203.gif" />
 * <p>
 * so leading to...
 * <pre>
 * &lt;amount> * 1/(1+&lt;rate>)^&lt;periods>
 * </pre>
 * 
 * @see http://www.financeformulas.net/Present_Value.html
 * @author Anatole Tresch
 * 
 */
public class PresentValue implements MonetaryOperator,
		CompoundCalculation<MonetaryAmount> {

	private int periods;
	private Rate rate;
	private BigDecimal divisor;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(PresentValue.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("amount", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public PresentValue(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		divisor = (BigDecimal.ONE.add(rate.get())).pow(periods);
	}

	/**
	 * The divisor to be divided thru to get the present value from an amount a, where
	 * {@code f(a) = a / divisor}.
	 * 
	 * @return the futer value factor based on the given rate and period.
	 */
	public BigDecimal getDivisor() {
		return divisor;
	}

	/**
	 * The number of periods.
	 * 
	 * @return the number of periods.
	 */
	public int getPeriods() {
		return periods;
	}

	/**
	 * The rate of return.
	 * 
	 * @return the rate of return.
	 */
	public Rate getRate() {
		return rate;
	}

	/**
	 * Calculates the present value for a given amount.
	 * 
	 * @return the present value, discounted for the periods, based on the given rate.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends MonetaryAmount> T apply(T amount) {
		return (T) amount.divide(divisor);
	}

	@Override
	public CompoundType getInputTape() {
		return INPUT_TYPE;
	}

	@Override
	public Class getResultType() {
		return MonetaryAmount.class;
	}

	@Override
	public MonetaryAmount calculate(CompoundValue input) {
		INPUT_TYPE.checkInput(input);
		Rate rate = input.get("rate", Rate.class);
		int period = input.get("periods", Integer.class);
		MonetaryAmount amount = input.get("amount", MonetaryAmount.class);
		BigDecimal divisor = (BigDecimal.ONE.add(rate.get())).pow(periods);
		return Money.from(amount).divide(divisor);
	}
}
