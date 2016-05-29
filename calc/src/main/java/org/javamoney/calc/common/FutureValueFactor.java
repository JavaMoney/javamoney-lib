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
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Future%20Value%20Factor%201.gif" />
 * <p>
 * The formula for the future value factor is used to calculate the future value of an amount per
 * dollar of its present value. The future value factor is generally found on a table which is used
 * to simplify calculations for amounts greater than one dollar (see example below). The future
 * value factor formula is based on the concept of time value of money. The concept of time value of
 * money is that an amount today is worth more than if that same nominal amount is received at a
 * future date. Any amount received today can be invested and receive earnings, as opposed to
 * waiting to receive the same amount with no earnings. An amount of $105 to be received a year from
 * now may be okay if the individual wants $100 today, assuming that the individual can earn 5%
 * otherwise in one year.
 *
 * @author Anatole Tresch
 * @see http://www.financeformulas.net/Future-Value-Factor.html
 */
public final class FutureValueFactor {

    private FutureValueFactor() {
    }

    public static BigDecimal calculate(Rate rate, int periods) {
        if (rate == null) {
            throw new IllegalArgumentException("rate required.");
        }
        // 1/((1+r)^n)
        BigDecimal base = CalculationContext.one().add(rate.get());
        return base.pow(periods, CalculationContext.mathContext());
    }

}
