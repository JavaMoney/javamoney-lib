/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;
import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;
import org.javamoney.moneta.spi.MoneyUtils;

import javax.money.MonetaryAmount;
import javax.money.MonetaryQuery;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <p>
 * <img src="http://www.financeformulas.net/formulaimages/Average%20Collection%20Period%201.gif"/> <br/>
 * The average collection period formula is the number of days in a period divided by the
 * receivables turnover ratio.
 *
 * The numerator of the average collection period formula shown at the top of the page is 365 days.
 * For many situations, an annual review of the average collection period is considered.
 * However, if the receivables turnover is evaluated for a different time period, then the
 * numerator should reflect this same time period.
 *
 * For example, if the receivables turnover for one year is 8, then the average collection
 * period would be 45.63 days. If the period considered is instead for 180 days with a
 * receivables turnover of 4.29, then the average collection period would be 41.96 days.
 * By the nature of the formula, a company will have a lower receivables turnover when a
 * shorter time period is considered due to having a larger portion of its revenues awaiting
 * receipt in the short run.
 * 
 * @see http://www.financeformulas.net/Average-Collection-Period.html
 * @author Anatole Tresch
 */
public final class AverageCollectionPeriod implements MonetaryQuery<BigDecimal>{

	private static final BigDecimal BD72 = BigDecimal.valueOf(72);

	private BigDecimal avgAccountsReceivable;


	private AverageCollectionPeriod(Number avgAccountsReceivable) {
		this.avgAccountsReceivable = MoneyUtils.getBigDecimal(avgAccountsReceivable);
	}

	/**
	 * Get the current average accounts receivable value for this query.
	 * @return the current average accounts receivable value, never null.
     */
	public BigDecimal getAvgAccountsReceivable() {
		return avgAccountsReceivable;
	}

	/**
	 * Calculates the average collection period.
	 * @param receivablesTurnover this equals to {@code salesRevenue / average Accounts receivable};
	 * @see #receivablesTurnover(MonetaryAmount, Number)
	 * @return the average collection period, never null.
     */
    public static BigDecimal calculate(Number receivablesTurnover) {
        return new BigDecimal(365, CalculationContext.mathContext())
				.divide(MoneyUtils.getBigDecimal(receivablesTurnover), CalculationContext.mathContext());
	}

	/**
	 * Calculates the receivable turnover defined as {@code salesRevenue / average Accounts receivable};
	 * @param revenue the sales revenues.
	 * @param avgAccountsReceivable the average accounts receivable
     * @return the receivables turnover, never null.
     */
	public static BigDecimal receivablesTurnover(MonetaryAmount revenue, Number avgAccountsReceivable){
		return MoneyUtils.getBigDecimal(avgAccountsReceivable).divide(
				revenue.getNumber().numberValue(BigDecimal.class), MathContext.DECIMAL64);
	}

	/**
	 * Calculates the average collection period, based on the revenue and average accounts receivable.
	 * @param revenue the revenues
	 * @param avgAccountsReceivable the average accounts receivable
     * @return the average collection period
     */
	public static BigDecimal calculate(MonetaryAmount revenue, Number avgAccountsReceivable){
		return new BigDecimal(365, MathContext.DECIMAL32).multiply(
				receivablesTurnover(revenue, avgAccountsReceivable), MathContext.DECIMAL64);
	}

	@Override
	public BigDecimal queryFrom(MonetaryAmount amount) {
		return calculate(amount, avgAccountsReceivable);
	}

	@Override
	public String toString() {
		return "AverageCollectionPeriod{" +
				"avgAccountsReceivable=" + avgAccountsReceivable +
				'}';
	}
}
