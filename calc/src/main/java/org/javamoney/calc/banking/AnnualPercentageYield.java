/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.banking;

import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;
import org.javamoney.calc.common.Rate;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * <img src= "http://www.financeformulas.net/formulaimages/APY%201.gif" />
 * <p>
 * The Annual Percentage Yield (APY), referenced as the effective annual rate in finance,
 * is the rate of interest that is earned when taking into consideration the effect of
 * compounding.
 *
 *
 * There are various terms used when compounding is not considered including nominal interest
 * rate, stated annual interest rate, and annual percentage rate(APR).
 *
 * In the formula, the stated interest rate is shown as r. A bank may show this as simply
 * "interest rate". The annual percentage yield formula would be applied to determine
 * what the effective yield would be if the account was compounded given the stated rate.
 * The n in the annual percentage yield formula would be the number of times that the
 * financial institution compounds. For example, if a financial institution compounds
 * the account monthly, n would equal 12.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @see http://www.financeformulas.net/Compound_Interest.html
 */
public final class AnnualPercentageYield implements MonetaryOperator {

    private static final ComplexType INPUT_TYPE = new ComplexType.Builder("AnnualPercentageYield:IN")
            .addRequiredParameter("amount", MonetaryAmount.class)
            .addRequiredParameter("rate", Rate.class)
            .addRequiredParameter("periods", Number.class).build();

    public static final ComplexCalculation<ComplexType, MonetaryAmount> CALCULATION = new ComplexCalculation<ComplexType, MonetaryAmount>() {
        @Override
        public ComplexType getInputType() {
            return INPUT_TYPE;
        }

        @Override
        public Class<MonetaryAmount> getResultType() {
            return MonetaryAmount.class;
        }

        @Override
        public MonetaryAmount calculate(ComplexValue<ComplexType> input) {
            return AnnualPercentageYield.calculate(input.get("amount", MonetaryAmount.class),
                    input.get("rate", Rate.class),
                    input.get("periods", Number.class).intValue()
            );
        }
    };

    /**
     * the target rate, not null.
     */
    private final Rate rate;
    /**
     * the periods, >= 0.
     */
    private final int periods;


    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private AnnualPercentageYield(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    public int getPeriods() {
        return periods;
    }

    public Rate getRate() {
        return rate;
    }


    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the operator, never null.
     */
    public static AnnualPercentageYield of(Rate rate, int periods){
        return new AnnualPercentageYield(rate, periods);
    }

    /**
     * Performs the calculation.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static Rate calculate(Rate rate, int periods) {
        if(periods==0){
            return Rate.zero(toString(rate, periods));
        }
        final BigDecimal ONE = new BigDecimal(1, MathContext.DECIMAL64);
        BigDecimal baseFactor = rate.get().divide(BigDecimal.valueOf(periods),MathContext.DECIMAL64)
                .add(ONE);
        return Rate.of(baseFactor.pow(periods).subtract(ONE),
                toString(rate, periods));
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the base amount, not null.
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods) {
        if(periods==0){
            return amount.getFactory().setNumber(0.0).create();
        }
        final BigDecimal ONE = new BigDecimal(1, MathContext.DECIMAL64);
        BigDecimal baseFactor = rate.get().divide(BigDecimal.valueOf(periods),MathContext.DECIMAL64)
                .add(ONE);
        return amount.multiply(baseFactor.pow(periods).subtract(ONE));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return toString(this.rate, this.periods);
    }

    /** Creates the toString String also used for detailed rate info.*/
    private static String toString(Rate rate, int periods) {
        return "AnnualPercentageYield{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
