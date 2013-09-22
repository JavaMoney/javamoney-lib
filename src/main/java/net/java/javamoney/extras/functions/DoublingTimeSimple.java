package net.java.javamoney.extras.functions;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;

import net.java.javamoney.extras.Rate;

/**
 * The doubling time for simple interest is simply 1 divided by the periodic
 * rate. The formula for doubling time with simple interest is used to calculate
 * how long it would take to double the balance on an interesting bearing
 * account that has simple interest. Simple interest is interest earned based
 * solely on the principle. In contrast, compound interest is interest earned on
 * principle along with prior interest earned.
 * 
 * @see http://www.financeformulas.net/Doubling-Time-Simple-Interest.html
 * @author Anatole Tresch
 */
public class DoublingTimeSimple implements MonetaryFunction<Rate, BigDecimal> {

	/**
	 * This function returnes the number of periods required to double an amount
	 * with continous compounding, given a rate.
	 */
	@Override
	public BigDecimal apply(Rate rate) {
		return BigDecimal.ONE.divide(rate.getRate());
	}

}
