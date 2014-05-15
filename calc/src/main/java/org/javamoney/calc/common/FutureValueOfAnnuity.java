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

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * <p>
 * <img src= "http://www.financeformulas.net/Formula%20Images/FV%20of%20Annuity%204.gif" />
 * <p>
 * The future value of an annuity formula is used to calculate what the value at a future date would
 * be for a series of periodic payments. The future value of an annuity formula assumes that
 * <p>
 * <nl>
 * <li>The rate does not change
 * <li>The first payment is one period away
 * <li>The periodic payment does not change
 * </nl>
 * If the rate or periodic payment does change, then the sum of the future value of each individual
 * cash flow would need to be calculated to determine the future value of the annuity. If the first
 * cash flow, or payment, is made immediately, the {@link org.javamoney.calc.common.FutureValue} formula would be used.
 *
 * @author Anatole
 * @author Werner
 * @see http://www.financeformulas.net/Future_Value_of_Annuity.html
 */
public final class FutureValueOfAnnuity extends AbstractPeriodicalFunction{

    private static final FutureValueOfAnnuity INSTANCE = new FutureValueOfAnnuity();

    private FutureValueOfAnnuity(){
    }

    public static final FutureValueOfAnnuity of(){
        return INSTANCE;
    }

    @Override
    public MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods){
        // Am * (((1 + r).pow(n))-1/rate)
        return amount.multiply(BigDecimal.ONE.add(rate.get()).pow(periods).subtract(BigDecimal.ONE).divide(rate.get()));
    }

}
