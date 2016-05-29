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

import javax.money.MonetaryException;
import java.math.BigDecimal;
import java.math.MathContext;


/**
 * The doubling time for simple interest is simply 1 divided by the periodic
 * rate. The formula for doubling time with simple interest is used to calculate
 * how long it would take to double the balance on an interesting bearing
 * account that has simple interest. Simple interest is interest earned based
 * solely on the principle. In contrast, compound interest is interest earned on
 * principle along with prior interest earned.
 *
 * @author Anatole Tresch
 * @see http://www.financeformulas.net/Doubling-Time-Simple-Interest.html
 */
public final class DoublingTimeSimple {

    private DoublingTimeSimple() {
    }

    /**
     * This function returns the number of periods required to double an amount
     * with continuous compounding, given a rate.
     */
    public static BigDecimal calculate(Rate rate) {
        if(rate.get().signum()==0){
            throw new MonetaryException("Cannot calculate DoublingTimeSimple with a rate=zero");
        }
        return CalculationContext.one().
                divide(rate.get(), CalculationContext.mathContext());
    }

}
