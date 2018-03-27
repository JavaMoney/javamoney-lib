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

/**
 * @author Anatole
 * @author Werner
 */
public final class DiscountFactor {

    /**
     * Private constructor.
     */
    private DiscountFactor() {
    }

    /**
     * Calculates the discount factor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     * @return the factor calculated.
     */
    public static BigDecimal calculate(RateAndPeriods rateAndPeriods) {
        Objects.requireNonNull(rateAndPeriods);
        // (1-(1+r)^n)/1-(1+rate)
        final BigDecimal ONE = CalculationContext.one();
        BigDecimal div = ONE.min(ONE.add(rateAndPeriods.getRate().get()));
        BigDecimal factor = ONE.subtract(ONE.add(rateAndPeriods.getRate().get()).pow(rateAndPeriods.getPeriods()))
                .divide(div, CalculationContext.mathContext());
        return ONE.add(factor);
    }

}
