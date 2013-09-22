package net.java.javamoney.extras;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

public class DiscountFactor implements MonetaryOperator {

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	private DiscountFactor(Rate rate, int periods) {
		this.periods = periods;
		if (rate == null) {
			throw new IllegalArgumentException("rate required.");
		}
		this.rate = rate;
		// (1-(1+r)^n)/1-(1+rate)
		BigDecimal div = BigDecimal.ONE
				.min(BigDecimal.ONE.add(rate.getRate()));
		factor = BigDecimal.ONE.subtract(
				BigDecimal.ONE.add(rate.getRate()).pow(periods)).divide(div);
		factor = BigDecimal.ONE.add(factor);
	}

	public static DiscountFactor of(Rate rate, int periods) {
		return new DiscountFactor(rate, periods);
	}

	public BigDecimal getFactor() {
		return factor;
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.multiply(factor);
	}

}
