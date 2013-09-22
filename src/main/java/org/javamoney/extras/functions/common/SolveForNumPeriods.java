package org.javamoney.extras.functions.common;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

import org.javamoney.extras.functions.CompoundFunction;
import org.javamoney.extras.functions.CompoundType;
import org.javamoney.extras.functions.CompoundValue;

/**
 * The formula for solving for the number of periods shown at the top of this
 * page is used to calculate the length of time required for a single cash
 * flow(present value) to reach a certain amount(future value) based on the time
 * value of money. In other words, this formula is used to calculate the length
 * of time a present value would need to reach the future value, given a certain
 * interest rate. The formula for solving for number of periods may also be
 * referred to as solving for n, solving for term, or solving for time. Solving
 * for n originates from the present value and future value formulas in which
 * the variable n denotes the number of periods. It is important to keep in mind
 * that the number of periods and periodic rate should match one another. For
 * example, if the rate is compounded monthly, then the monthly rate would be
 * used and the number of periods would reflect the number of months.
 * 
 * @see http
 *      ://www.financeformulas.net/Solve-for-Number-of-Periods-PV-and-FV.html
 * @author Anatole Tresch
 */
public class SolveForNumPeriods implements MonetaryFunction<Rate, BigDecimal>,
		CompoundFunction<BigDecimal> {

	private MonetaryAmount presentValue;
	private MonetaryAmount futureValue;
	private int periods;

	private static final CompoundType INPUT_TYPE = new CompoundType.Builder()
			.withIdForInput(SimpleInterest.class)
			.withRequiredArg("periods", Integer.class)
			.withRequiredArg("presentValue", MonetaryAmount.class)
			.withRequiredArg("futureValue", MonetaryAmount.class)
			.withRequiredArg("rate", Rate.class).build();

	public SolveForNumPeriods(MonetaryAmount presentValue,
			MonetaryAmount futureValue, int periods) {
		if (presentValue == null) {
			throw new IllegalArgumentException("presentValue required.");
		}
		if (futureValue == null) {
			throw new IllegalArgumentException("futureValue required.");
		}
		this.presentValue = presentValue;
		this.futureValue = futureValue;
	}

	/**
	 * @return the presentValue
	 */
	public final MonetaryAmount getPresentValue() {
		return presentValue;
	}

	/**
	 * @return the futureValue
	 */
	public final MonetaryAmount getFutureValue() {
		return futureValue;
	}

	/**
	 * @return the periods
	 */
	public final int getPeriods() {
		return periods;
	}

	@Override
	public BigDecimal apply(Rate rate) {
		MonetaryAmount pv = new PresentValue(rate, periods).apply(presentValue);
		MonetaryAmount fv = new PresentValue(rate, periods).apply(futureValue);
		BigDecimal count = BigDecimal.valueOf(Math.log(futureValue
				.doubleValue() / presentValue.doubleValue()));
		BigDecimal divisor = BigDecimal.valueOf(Math.log(1 + rate.getRate()
				.doubleValue()));
		return count.divide(divisor);
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
		Rate rate = input.get("rate", Rate.class);
		int period = input.get("periods", Integer.class);
		MonetaryAmount pv = input.get("presentValue", MonetaryAmount.class);
		MonetaryAmount fv = input.get("futureValue", MonetaryAmount.class);
		BigDecimal count = BigDecimal.valueOf(Math.log(futureValue
				.doubleValue() / presentValue.doubleValue()));
		BigDecimal divisor = BigDecimal.valueOf(Math.log(1 + rate.getRate()
				.doubleValue()));
		return count.divide(divisor);
	}
}
