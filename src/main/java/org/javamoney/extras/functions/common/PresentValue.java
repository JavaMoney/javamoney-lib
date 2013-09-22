package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.extras.functions.CompoundFunction;
import org.javamoney.extras.functions.CompoundType;
import org.javamoney.extras.functions.CompoundValue;

public class PresentValue implements MonetaryOperator,
		CompoundFunction<MonetaryAmount> {

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
		BigDecimal divisor = (BigDecimal.ONE.add(rate.getRate())).pow(periods);
		return amount.divide(divisor);
	}
}
