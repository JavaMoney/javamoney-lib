/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *  and individual contributors by the @author tags.
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

import javax.money.MonetaryAmount;
import javax.money.MonetaryAdjuster;
import javax.money.Money;

import org.javamoney.function.CompoundFunction;
import org.javamoney.function.CompoundType;
import org.javamoney.function.CompoundValue;

/**
 * Future Value (FV) is a formula used in finance to calculate the value of a
 * cash flow at a later date than originally received. This idea that an amount
 * today is worth a different amount than at a future time is based on the time
 * value of money. The time value of money is the concept that an amount
 * received earlier is worth more than if the same amount is received at a later
 * time. For example, if one was offered $100 today or $100 five years from now,
 * the idea is that it is better to receive this amount today. The opportunity
 * cost for not having this amount in an investment or savings is quantified
 * using the future value formula. If one wanted to determine what amount they
 * would like to receive one year from now in lieu of receiving $100 today, the
 * individual would use the future value formula. See example at the bottom of
 * the page. The future value formula also looks at the effect of compounding.
 * Earning .5% per month is not the same as earning 6% per year, assuming that
 * the monthly earnings are reinvested. As the months continue along, the next
 * month's earnings will make additional monies on the earnings from the prior
 * months. For example, if one earns interest of $40 in month one, the next
 * month will earn interest on the original balance plus the $40 from the
 * previous month. This is known as compound interest.
 * 
 * @see http://www.financeformulas.net/Future_Value.html
 *      <p>
 *      <img src=
 *      "http://www.financeformulas.net/Formula%20Images/Future%20Value%201.gif"
 *      />
 *      <p>
 *      or...
 * 
 *      <pre>
 * FV(&lt;amount>)  = &lt;amount> * ((1 + &lt;rate>).pow(&lt;periods>))
 * </pre>
 * @author Anatole Tresch
 */
public class FutureValue implements MonetaryAdjuster,
		CompoundFunction<MonetaryAmount> {

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(FutureValue.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("amount", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public FutureValue(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		factor = (BigDecimal.ONE.add(rate.getRate())).pow(periods);
	}

	/**
	 * The factor to be multiplied to get the future value from an amount a,
	 * where {@code f(a) = factor * a}.
	 * 
	 * @return the futer value factor based on the given rate and period.
	 */
	public BigDecimal getFactor() {
		return factor;
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
	 * Calculates the future value ov {@code value}. The value hereby represents
	 * the cash flow at period 0, which is upcounted for n periods.
	 */
	@Override
	public MonetaryAmount adjustInto(MonetaryAmount amount) {
		return Money.from(amount).multiply(factor);
	}

	@Override
	public CompoundType getInputTape() {
		return INPUT_TYPE;
	}

	@Override
	public Class<MonetaryAmount> getResultType() {
		return MonetaryAmount.class;
	}

	@Override
	public MonetaryAmount calculate(CompoundValue input) {
		INPUT_TYPE.checkInput(input);
		Rate rate = input.get("rate", Rate.class);
		int period = input.get("periods", Integer.class);
		MonetaryAmount amount = input.get("amount", MonetaryAmount.class);
		BigDecimal f = (BigDecimal.ONE.add(rate.getRate())).pow(periods);
		return Money.from(amount).multiply(f);
	}

}
