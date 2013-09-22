package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.extras.functions.CompoundFunction;
import org.javamoney.extras.functions.CompoundType;
import org.javamoney.extras.functions.CompoundValue;

/**
 * The future value of an annuity formula is used to calculate what the value at
 * a future date would be for a series of periodic payments. The future value of
 * an annuity formula assumes that
 * 
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value
 * of each individual cash flow would need to be calculated to determine the
 * future value of the annuity. If the first cash flow, or payment, is made
 * immediately, the future value of annuity due formula would be used.
 * <p>
 * <img src=
 * "http://www.financeformulas.net/Formula%20Images/FV%20of%20Annuity%204.gif"
 * />
 * <p>
 * or...
 * 
 * <pre>
 * &lt;amount> * (((1 + &lt;rate>).pow(&lt;periods>))-1/&lt;rate>)
 * </pre>
 * 
 * @see http://www.financeformulas.net/Future_Value_of_Annuity.html
 * @author Anatole
 * 
 */
public class FutureValueOfAnnuity implements MonetaryOperator,
		CompoundFunction<MonetaryAmount> {

	private Rate rate;
	private int periods;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(FutureValueOfAnnuity.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("amount", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public FutureValueOfAnnuity(Rate rate, int periods) {
		if (rate == null) {
			throw new IllegalArgumentException("rate null.");
		}
		this.rate = rate;
		this.periods = periods;
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		// Am * (((1 + r).pow(n))-1/rate)
		return value.multiply(BigDecimal.ONE.add(rate.getRate()).pow(periods)
				.subtract(BigDecimal.ONE).divide(rate.getRate()));
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
		return amount.multiply(BigDecimal.ONE.add(rate.getRate()).pow(periods)
				.subtract(BigDecimal.ONE).divide(rate.getRate()));
	}
}
