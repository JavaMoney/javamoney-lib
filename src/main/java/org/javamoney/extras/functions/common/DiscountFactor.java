package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;

import org.javamoney.extras.functions.CompoundFunction;
import org.javamoney.extras.functions.CompoundType;
import org.javamoney.extras.functions.CompoundValue;

/**
 * 
 * @author Anatole
 * 
 */
public class DiscountFactor implements MonetaryFunction<Integer, BigDecimal>,
		CompoundFunction<BigDecimal> {

	private Rate rate;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(DiscountFactor.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("rate", Rate.class).build();

	private DiscountFactor(Rate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("rate required.");
		}
		this.rate = rate;
	}

	public static DiscountFactor of(Rate rate) {
		return new DiscountFactor(rate);
	}

	public Rate getRate() {
		return rate;
	}

	@Override
	public BigDecimal apply(Integer periods) {
		// (1-(1+r)^n)/1-(1+rate)
		BigDecimal div = BigDecimal.ONE
				.min(BigDecimal.ONE.add(rate.getRate()));
		BigDecimal factor = BigDecimal.ONE.subtract(
				BigDecimal.ONE.add(rate.getRate()).pow(periods)).divide(div);
		return BigDecimal.ONE.add(factor);
	}

	@Override
	public CompoundType getInputTape() {
		return INPUT_TYPE;
	}

	@Override
	public Class<BigDecimal> getResultType() {
		return BigDecimal.class;
	}

	@Override
	public BigDecimal calculate(CompoundValue input) {
		INPUT_TYPE.checkInput(input);
		Integer periods = input.get("periods", Integer.class);
		Rate rate = input.get("rate", Rate.class);
		BigDecimal div = BigDecimal.ONE
				.min(BigDecimal.ONE.add(rate.getRate()));
		BigDecimal factor = BigDecimal.ONE.subtract(
				BigDecimal.ONE.add(rate.getRate()).pow(periods)).divide(div);
		return BigDecimal.ONE.add(factor);
	}

}
