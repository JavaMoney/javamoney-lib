package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;



/**
 * The formula for the future value factor is used to calculate the future value
 * of an amount per dollar of its present value. The future value factor is
 * generally found on a table which is used to simplify calculations for amounts
 * greater than one dollar (see example below). The future value factor formula
 * is based on the concept of time value of money. The concept of time value of
 * money is that an amount today is worth more than if that same nominal amount
 * is received at a future date. Any amount received today can be invested and
 * receive earnings, as opposed to waiting to receive the same amount with no
 * earnings. An amount of $105 to be received a year from now may be okay if the
 * individual wants $100 today, assuming that the individual can earn 5%
 * otherwise in one year.
 * 
 * @see http://www.financeformulas.net/Future-Value-Factor.html
 * @author Anatole
 */
public class PresentValueFactor implements MonetaryFunction<Rate, BigDecimal> {

	private int periods;

	private PresentValueFactor(int periods) {
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
		// (1+r)^n
		return BigDecimal.ONE.add(rate.getRate()).pow(periods);
	}

}
