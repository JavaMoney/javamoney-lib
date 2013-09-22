package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;



/**
 * The compound interest formula calculates the amount of interest earned on an
 * account or investment where the amount earned is reinvested. By reinvesting
 * the amount earned, an investment will earn money based on the effect of
 * compounding. Compounding is the concept that any amount earned on an
 * investment can be reinvested to create additional earnings that would not be
 * realized based on the original principal, or original balance, alone. The
 * interest on the original balance alone would be called simple interest. The
 * additional earnings plus simple interest would equal the total amount earned
 * from compound interest.
 * <p>
 * <img src="http://www.financeformulas.net/Formula%20Images/Compound%20Interest%201.gif" />
 * <p>
 * or...
 * <pre>
 * factor = [(1+&lt;rate>).pow(&lt;periods>))-1
 * f(&lt;amount> = &lt;amount> * &lt;factor>
 * </ptr>
 * @see http://www.financeformulas.net/Compound_Interest.html
 * @author Anatole Tresch
 */
public class CompundInterest implements MonetaryOperator {

	private int periods;
	private Rate rate;
	private BigDecimal factor;

	public CompundInterest(Rate rate, int periods) {
		this.rate = rate;
		this.periods = periods;
		factor = BigDecimal.ONE.add(rate.getRate()).pow(periods).subtract(
				BigDecimal.ONE);
	}

	/**
	 * The factor to be multiplied to get the compounded value from an amount a,
	 * where {@code f(a) = factor * a}.
	 * 
	 * @return the compounded interest value factor based on the given rate and
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
	 * Calculates the compounded value of {@code value}. The result hereby
	 * represents the compounded value and interest with the given rate and for
	 * n periods.
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.multiply(factor);
	}

}
