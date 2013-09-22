package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryFunction;



/**
 * The formula for doubling time with continuous compounding is used to
 * calculate the length of time it takes doubles one's money in an account or
 * investment that has continuous compounding. It is important to note that this
 * formula will return a time to double based on the term of the rate. For
 * example, if the monthly rate is used, the answer to the formula will return
 * the number of months it takes to double. If the annual rate is used, the
 * answer will then reflect the number of years to double.
 * 
 * @see http://www.financeformulas.net/Doubling-Time-Continuous-Compounding.html
 * @author Anatole Tresch
 */
public class DoublingTimeCompounding implements MonetaryFunction<Rate, BigDecimal> {

	/**
	 * This function returnes the number of periods required to double an amount
	 * with continous compounding, given a rate.
	 */
	@Override
	public BigDecimal apply(Rate rate) {
		return BigDecimal.valueOf(Math.log(2.0d)).divide(rate.getRate());
	}

}
