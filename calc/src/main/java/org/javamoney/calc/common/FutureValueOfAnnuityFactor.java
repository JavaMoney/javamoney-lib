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

import static org.javamoney.calc.CalculationContext.one;
import static org.javamoney.calc.CalculationContext.mathContext;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The present value of annuity formula determines the value of a series of future periodic payments
 * at a given time. The present value of annuity formula relies on the concept of time value of
 * money, in that one dollar present day is worth more than that same dollar at a future date.
 *
 * <b>Rate Per Period</b>
 *
 * As with any financial formula that involves a rate, it is important to make sure that the rate is
 * consistent with the other variables in the formula. If the payment is per month, then the rate
 * needs to be per month, and similarly, the rate would need to be the annual rate if the payment is
 * annual.
 *
 * An example would be an annuity that has a 12% annual rate and payments are made monthly. The
 * monthly rate of 1% would need to be used in the formula.
 *
 * @author Anatole Tresch
 * @see <a href="http://www.financeformulas.net/Present_Value_of_Annuity.html">http://www.financeformulas.net/Present_Value_of_Annuity.html</a>
 */
public final class FutureValueOfAnnuityFactor {

	private FutureValueOfAnnuityFactor() {
	}

    /**
     * Calculate big decimal.
     *
     * @param rateAndPeriods the rate and periods
     * @return the big decimal
     */
    public static BigDecimal calculate(RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(rateAndPeriods, "RateAndPeriods required.");
		// PVofA = P * [ (1 - (1 + r).pow(-n)) / r ]
		Rate rate = rateAndPeriods.getRate();
		int periods = rateAndPeriods.getPeriods();
		BigDecimal subtractor = one().divide(one().add(rate.get()).pow(periods), mathContext());
		return one().subtract(subtractor)
				.divide(rate.get(), mathContext());
	}

}
