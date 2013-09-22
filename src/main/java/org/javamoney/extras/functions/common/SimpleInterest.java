package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;



public class SimpleInterest implements MonetaryOperator {

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	public SimpleInterest(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		factor = BigDecimal.ONE.add(rate.getRate().multiply(
				BigDecimal.valueOf(periods)));
	}

	/**
	 * The factor to be multiplied to get the new value from an amount a, where
	 * {@code f(a) = factor * a}.
	 * 
	 * @return the simple interest value factor based on the given rate and
	 *         period.
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
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.multiply(factor);
	}

}
