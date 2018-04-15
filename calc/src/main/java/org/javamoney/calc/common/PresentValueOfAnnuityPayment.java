/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: @atsticks, @keilw
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;

import java.math.BigDecimal;
import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * The annuity payment formula is used to calculate the periodic payment on an annuity. An annuity
 * is a series of periodic payments that are received at a future date. The present value portion of
 * the formula is the initial payout, with an example being the original payout on an amortized
 * loan.
 *
 * The annuity payment formula shown is for ordinary annuities. This formula assumes that the rate
 * does not change, the payments stay the same, and that the first payment is one period away. An
 * annuity that grows at a proportionate rate would use the growing annuity payment formula.
 * Otherwise, an annuity that changes the payment and/or rate would need to be adjusted for each
 * change. An annuity that has its first payment due at the beginning would use the annuity due
 * payment formula and the deferred annuity payment formula would have a payment due at a later
 * date.
 *
 * The annuity payment formula can be used for amortized loans, income annuities, structured
 * settlements, lottery payouts(see annuity due payment formula if first payment starts
 * immediately), and any other type of constant periodic payments.
 *
 * This can be rewritten as:
 * @see <a href="http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%203.gif">http://www.financeformulas.net/Formula%20Images/Annuity%20-%20Payment%203.gif</a>
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @see <a href="http://www.financeformulas.net/Annuity_Payment_Formula.html">http://www.financeformulas.net/Annuity_Payment_Formula.html</a>
 */
public final class PresentValueOfAnnuityPayment extends AbstractRateAndPeriodBasedOperator {

    /**
     * Private constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    private PresentValueOfAnnuityPayment(RateAndPeriods rateAndPeriods) {
        super(rateAndPeriods);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param rateAndPeriods The discount rate and periods, not null.
     * @return the operator, never null.
     */
    public static PresentValueOfAnnuityPayment of(RateAndPeriods rateAndPeriods) {
        return new PresentValueOfAnnuityPayment(rateAndPeriods);
    }

    /**
     * Performs the calculation.
     *
     * @param amount         the first payment
     * @param rateAndPeriods The rate and periods, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount amount, RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(rateAndPeriods, "Rate required");
        Rate rate = rateAndPeriods.getRate();
        int periods = rateAndPeriods.getPeriods();
        // AP(m) = PV(m,r,n) / [ (1-((1 + r).pow(-n))) / r ]
        return PresentValue.calculate(amount, rateAndPeriods).divide(
                BigDecimal.ONE.subtract((BigDecimal.ONE.add(rate.get())
                        .pow(-1 * periods, CalculationContext.mathContext()).
                                divide(rate.get(), CalculationContext.mathContext())
                )));
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        return calculate(amount, rateAndPeriods);
    }

    @Override
    public String toString() {
        return "PresentValueAnnuityPayment{" +
                "rateAndPeriods=" + rateAndPeriods +
                '}';
    }
}
