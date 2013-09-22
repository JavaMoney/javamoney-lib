package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;



/**
 * The formula for the present value factor is used to calculate the present
 * value per dollar that is received in the future. The present value factor
 * formula is based on the concept of time value of money. Time value of money
 * is the idea that an amount received today is worth more than if the same
 * amount was received at a future date. Any amount received today can be
 * invested to earn additional monies.
 * 
 * @see http://www.financeformulas.net/Present_Value_Factor.html
 * @author Anatole
 */
public class FutureValueFactor implements MonetaryFunction<Rate, BigDecimal> {

	private int periods;

	private FutureValueFactor(int periods) {
		this.periods = periods;
	}

	public int getPeriods() {
		return periods;
	}

	@Override
	public BigDecimal apply(Rate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("rate required.");
		}
		// 1/((1+r)^n)
		return BigDecimal.ONE.divide(
				BigDecimal.ONE.add(rate.getRate()).pow(periods));
	}

}
