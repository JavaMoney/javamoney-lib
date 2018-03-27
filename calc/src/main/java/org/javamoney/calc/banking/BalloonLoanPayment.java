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

import org.javamoney.calc.common.AbstractRateAndPeriodBasedOperator;
import org.javamoney.calc.common.Rate;
import org.javamoney.calc.common.RateAndPeriods;

import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import static org.javamoney.calc.CalculationContext.one;

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
 * @link http://www.financeformulas.net/Compound_Interest.html
 */
public final class BalloonLoanPayment extends AbstractRateAndPeriodBasedOperator{

//    private static final ValidatedMultiValue INPUT_TYPE = new ValidatedMultiValue.Builder("BalloonLoanPayment:IN")
//            .addRequiredParameter("balancePV", MonetaryAmount.class)
//            .addRequiredParameter("balloonAmount", MonetaryAmount.class)
//            .addRequiredParameter("rateAndPeriods", RateAndPeriods.class)
//            .build();
//
//    public static final ComplexCalculation<MultiValue, MonetaryAmount> CALCULATION = input ->
//            BalloonLoanPayment.calculate(input.get("balancePV", MonetaryAmount.class),
//                    input.get("balloonAmount", MonetaryAmount.class),
//                    input.get("rateAndPeriods", RateAndPeriods.class));

    /** The balloon amount. */
    private MonetaryAmount balloonAmount;

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private BalloonLoanPayment(RateAndPeriods rateAndPeriods, MonetaryAmount balloonAmount) {
        super(rateAndPeriods);
        if(rateAndPeriods.getPeriods()==0){
            throw new MonetaryException("Period cannot be 0.");
        }
        this.balloonAmount = Objects.requireNonNull(balloonAmount);
    }

    public MonetaryAmount getBalloonAmount(){
        return balloonAmount;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the operator, never null.
     */
    public static BalloonLoanPayment of(RateAndPeriods rateAndPeriods, MonetaryAmount balloonAmount){
        return new BalloonLoanPayment(rateAndPeriods, balloonAmount);
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amountPV) {
        if(!balloonAmount.getCurrency().equals(amountPV.getCurrency())){
            throw new MonetaryException("Currency mismatch: " + balloonAmount.getCurrency() +
                    " <> "+amountPV.getCurrency());
        }
        return calculate(amountPV, balloonAmount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "BalloonLoanPayment{" +
                "\n " + rateAndPeriods +
                ",\n balloonAmount=" + balloonAmount +
                '}';
    }

    /**
     * Performs the calculation.
     *
     * @param amountPV  the present value, not null.
     * @param balloonAmount the balloon amount, not null and currency compatible with {@code amountPV}.
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amountPV, MonetaryAmount balloonAmount,
                                           RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(rateAndPeriods);
        Rate rate = rateAndPeriods.getRate();
        int periods = rateAndPeriods.getPeriods();

        BigDecimal factor2 = rate.get().divide(
                one().subtract(
                        one().add(rate.get()).pow(-periods, MathContext.DECIMAL64)), MathContext.DECIMAL64);
        MonetaryAmount factor1 = amountPV.subtract(
                balloonAmount.getFactory().setNumber(
                        balloonAmount.getNumber().numberValue(BigDecimal.class).divide(
                        one().add(rate.get()).pow(periods, MathContext.DECIMAL64), MathContext.DECIMAL64)).create());
        return factor1.multiply(factor2);
    }


}
