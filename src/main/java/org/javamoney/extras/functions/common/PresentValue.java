package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;



public class PresentValue implements MonetaryOperator {

	private int periods;
	private Rate rate;
	private BigDecimal divisor;

	public PresentValue(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		divisor = (BigDecimal.ONE.add(rate.getRate())).pow(periods);
	}

	/**
	 * The divisor to be divided thru to get the present value from an amount a,
	 * where {@code f(a) = a / divisor}.
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
	 * @return the present value, discounted for the periods, based on the given
	 *         rate.
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.divide(divisor);
	}

}
