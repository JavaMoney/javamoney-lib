package net.java.javamoney.extras.functions;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;

import net.java.javamoney.extras.Rate;

/**
 * The Doubling Time formula is used in Finance to calculate the length of time
 * required to double an investment or money in an interest bearing account. It
 * is important to note that r in the doubling time formula is the rate per
 * period. If one wishes to calculate the amount of time to double their money
 * in a money market account that is compounded monthly, then r needs to express
 * the monthly rate and not the annual rate. The monthly rate can be found by
 * dividing the annual rate by 12. With this situation, the doubling time
 * formula will give the number of months that it takes to double money and not
 * years. In addition to expressing r as the monthly rate if the account is
 * compounded monthly, one could also use the effective annual rate, or annual
 * percentage yield, as r in the doubling time formula.
 * 
 * @see http://www.financeformulas.net/Doubling_Time.html
 * @author Anatole Tresch
 */
public class DoublingTime implements MonetaryFunction<Rate, BigDecimal> {

	/**
	 * This function returnes the number of periods required to double an amount
	 * with continous compounding, given a rate.
	 */
	@Override
	public BigDecimal apply(Rate rate) {
		return BigDecimal.valueOf(Math.log(2.0d)).divide(
				BigDecimal.valueOf(Math.log(1.0d)).add(rate.getRate()));
	}

}
