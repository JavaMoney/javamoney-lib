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
import javax.money.MonetaryException;
import javax.money.MonetaryOperator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * <img src= "http://www.financeformulas.net/formulaimages/Balloon%20Loan%20Payment%201.gif" />
 * <p>
 * The balloon loan payment formula is used to calculate the payments on a loan that has a balance
 * remaining after all periodic payments are made. Examples of loans that may use the balloon loan
 * payment formula would be auto leases, balloon mortgages, and any other form of loan not paid
 * in full at its end date.
 *
 * The formula for a balloon loan payment could also be used for any form of annuity where a
 * balance is left after all periodic cash flows are made. An annuity is simply a series of
 * periodic payments. An example of how this formula could be applied in a non-loan related
 * way would be if an individual has $11,000 sitting in their interest account that must last
 * them 2 years, and they need to have a balance of $5,000 at the end of the 2nd year. The
 * monthly amount withdrawn could be calculated using the balloon loan payment formula.
 *
 * One may be enticed to calculate the example above by simply subtracting $5,000 from $11,000
 * and calculating the payment based on an ordinary annuity of $6,000. However, one must
 * consider that the $5,000 will earn interest over the 2 years leaving a balance higher
 * than $5,000 after the 2nd year. This may be acceptable for a smaller amount or for quick
 * calculations in ordinary life, however it is not the exact way to calculate the periodic
 * payment.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @see http://www.financeformulas.net/Compound_Interest.html
 */
public final class BalloonLoanPayment implements MonetaryOperator{

    private static final ComplexType INPUT_TYPE = new ComplexType.Builder("BalloonLoanPayment:IN")
            .addRequiredParameter("balancePV", MonetaryAmount.class)
            .addRequiredParameter("balloonAmount", MonetaryAmount.class)
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
            return BalloonLoanPayment.calculate(input.get("balancePV", MonetaryAmount.class),
                    input.get("balloonAmount", MonetaryAmount.class),
                    input.get("rate", Rate.class),
                    input.get("periods", Number.class).intValue()
            );
        }
    };

    /**
     * the target rate, not null.
     */
    private final Rate rate;

    /**@Override
        public ComplexType getInputType() {
            return INPUT_TYPE;
        }

        @Override
        public Class<MonetaryAmount> getResultType() {
            return MonetaryAmount.class;
        }

        @Override
        public MonetaryAmount calculate(ComplexValue<ComplexType> input) {
            return ;
        }
     * the periods, >= 0.
     */
    private final int periods;

    /** The balloon amount. */
    private MonetaryAmount balloonAmount;

    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private BalloonLoanPayment(Rate rate, int periods, MonetaryAmount balloonAmount) {
        if (periods <= 0) {
            throw new MonetaryException("Periods for BalloonLoanPayment calculation <= 0");
        }
        this.rate = Objects.requireNonNull(rate);
        this.periods = periods;
        this.balloonAmount = Objects.requireNonNull(balloonAmount);
    }

    public int getPeriods() {
        return periods;
    }

    public Rate getRate() {
        return rate;
    }

    public MonetaryAmount getBalloonAmount(){
        return balloonAmount;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the operator, never null.
     */
    public static BalloonLoanPayment of(Rate rate, int periods, MonetaryAmount balloonAmount){
        return new BalloonLoanPayment(rate, periods, balloonAmount);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amountPV) {
        if(!balloonAmount.getCurrency().equals(amountPV.getCurrency())){
            throw new MonetaryException("Currency mismatch: " + balloonAmount.getCurrency() +
                    " <> "+amountPV.getCurrency());
        }
        return calculate(amountPV, balloonAmount, rate, periods);
    }

    @Override
    public String toString() {
        return "BalloonLoanPayment{" +
                "rate=" + rate +
                ", periods=" + periods +
                ", balloonAmount=" + balloonAmount +
                '}';
    }

    /**
     * Performs the calculation.
     *
     * @param amountPV  the present value, not null.
     * @param balloonAmount the balloon amount, not null and currency compatible with {@code amountPV}.
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amountPV, MonetaryAmount balloonAmount,
                                           Rate rate, int periods) {
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        final BigDecimal ONE = new BigDecimal(1, MathContext.DECIMAL64);
        BigDecimal factor2 = rate.get().divide(
                ONE.subtract(
                        ONE.add(rate.get()).pow(-periods, MathContext.DECIMAL64)), MathContext.DECIMAL64);
        MonetaryAmount factor1 = amountPV.subtract(
                balloonAmount.getFactory().setNumber(
                        balloonAmount.getNumber().numberValue(BigDecimal.class).divide(
                        ONE.add(rate.get()).pow(periods, MathContext.DECIMAL64), MathContext.DECIMAL64)).create());
        return factor1.multiply(factor2);
    }


}
