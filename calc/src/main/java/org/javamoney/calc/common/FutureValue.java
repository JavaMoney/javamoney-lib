/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;

import java.math.BigDecimal;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * Future Value (FV) is a formula used in finance to calculate the value of a cash flow at a later
 * date than originally received. This idea that an amount today is worth a different amount than at
 * a future time is based on the time value of money. The time value of money is the concept that an
 * amount received earlier is worth more than if the same amount is received at a later time. For
 * example, if one was offered $100 today or $100 five years from now, the idea is that it is better
 * to receive this amount today. The opportunity cost for not having this amount in an investment or
 * savings is quantified using the future value formula. If one wanted to determine what amount they
 * would like to receive one year from now in lieu of receiving $100 today, the individual would use
 * the future value formula. See example at the bottom of the page. The future value formula also
 * looks at the effect of compounding. Earning .5% per month is not the same as earning 6% per year,
 * assuming that the monthly earnings are reinvested. As the months continue along, the next month's
 * earnings will make additional monies on the earnings from the prior months. For example, if one
 * earns interest of $40 in month one, the next month will earn interest on the original balance
 * plus the $40 from the previous month. This is known as compound interest.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @link http://www.financeformulas.net/Future_Value.html
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/Future%20Value%201.gif" />
 * <p>
 * or...
 * <p>
 * <pre>
 * FV(&lt;amount>)  = &lt;amount> * ((1 + &lt;rate>).pow(&lt;periods>))
 * </pre>
 */
public final class FutureValue extends AbstractRateAndPeriodBasedOperator{

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private FutureValue(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the operator, never null.
     */
    public static FutureValue of(RateAndPeriods rateAndPeriods) {
        return new FutureValue(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the base amount, not null.
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        BigDecimal f = (CalculationContext.one().add(rateAndPeriods.getRate().get())).pow(rateAndPeriods.getPeriods());
        return amount.multiply(f);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "FutureValue{" +
                "\n " + rateAndPeriods +
                '}';
    }
}
