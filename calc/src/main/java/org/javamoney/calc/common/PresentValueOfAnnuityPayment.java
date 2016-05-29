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
 * <img src= "http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%201.gif" />
 * <p>
 * The annuity payment formula is used to calculate the periodic payment on an annuity. An annuity
 * is a series of periodic payments that are received at a future date. The present value portion of
 * the formula is the initial payout, with an example being the original payout on an amortized
 * loan.
 * <p>
 * The annuity payment formula shown is for ordinary annuities. This formula assumes that the rate
 * does not change, the payments stay the same, and that the first payment is one period away. An
 * annuity that grows at a proportionate rate would use the growing annuity payment formula.
 * Otherwise, an annuity that changes the payment and/or rate would need to be adjusted for each
 * change. An annuity that has its first payment due at the beginning would use the annuity due
 * payment formula and the deferred annuity payment formula would have a payment due at a later
 * date.
 * <p>
 * The annuity payment formula can be used for amortized loans, income annuities, structured
 * settlements, lottery payouts(see annuity due payment formula if first payment starts
 * immediately), and any other type of constant periodic payments.
 * <p>
 * This can be rewritten as:<br/>
 * http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%203.gif
 * 
 * @see http://www.financeformulas.net/Annuity_Payment_Formula.html
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public final class PresentValueOfAnnuityPayment implements MonetaryOperator {

    /**
     * the target rate, not null.
     */
    private Rate rate;
    /**
     * the periods, >= 0.
     */
    private int periods;

    /**
     * Private constructor.
     *
     * @param rate    the target rate, not null.
     * @param periods the periods, >= 0.
     */
    private PresentValueOfAnnuityPayment(Rate rate, int periods) {
        this.rate = Objects.requireNonNull(rate);
        if (periods < 0) {
            throw new IllegalArgumentException("Periods < 0");
        }
        this.periods = periods;
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rate The discount rate, not null.
     * @param periods      the target periods, >= 0.
     * @return the operator, never null.
     */
    public static PresentValueOfAnnuityPayment of(Rate rate, int periods) {
        return new PresentValueOfAnnuityPayment(rate, periods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount  the first payment
     * @param rate    The rate, not null.
     * @param periods the target periods, >= 0.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rate, "Rate required");
        // AP(m) = PV(m,r,n) / [ (1-((1 + r).pow(-n))) / r ]
        return PresentValue.calculate(amount, rate, periods).divide(
                BigDecimal.ONE.subtract((BigDecimal.ONE.add(rate.get())
                        .pow(-1 * periods, CalculationContext.mathContext()).
                                divide(rate.get(), CalculationContext.mathContext())
                )));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rate, periods);
    }

    @Override
    public String toString() {
        return "PresentValueAnnuityPayment{" +
                "rate=" + rate +
                ", periods=" + periods +
                '}';
    }
}
