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

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

/**
 * <img src= "http://www.financeformulas.net/Formula%20Images/Continuous%20Compounding%201.gif" />
 * <p>
 * The continuous compounding formula is used to determine the interest earned on an account that is
 * constantly compounded, essentially leading to an infinite amount of compounding periods. The
 * effect of compounding is earning interest on an investment, or at times paying interest on a
 * debt, that is reinvested to earn additional monies that would not have been gained based on the
 * principal balance alone. By earning interest on prior interest, one can earn at an exponential
 * rate. The continuous compounding formula takes this effect of compounding to the furthest limit.
 * Instead of compounding interest on an monthly, quarterly, or annual basis, continuous compounding
 * will effectively reinvest gains perpetually.
 *
 * @author Anatole
 * @author Werner
 */
public final class ContinuousCompoundInterest extends AbstractPeriodicalFunction{

    private static final ContinuousCompoundInterest INSTANCE = new ContinuousCompoundInterest();

    private ContinuousCompoundInterest(){
    }

    public static final ContinuousCompoundInterest of(){
        return INSTANCE;
    }

    @Override
    public MonetaryAmount calculate(MonetaryAmount amount, Rate rate, int periods){
        int power = rate.get().multiply(BigDecimal.valueOf(periods)).intValue();
        BigDecimal f = BigDecimal.valueOf(Math.E).pow(power);
        return amount.multiply(f);
    }

}
