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
 * Contributors: @manuela-grindei
 */
package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Bond%20Equivalent%20Yield.gif" />
 * <p> The bond equivalent yield formula is used to determine the annual yield on a discount, or zero coupon, bond.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Bond_Equivalent_Yield.html
 */
public final class BondEquivalentYield {

    /**
     * Private constructor.
     */
    private BondEquivalentYield() {
    }

    /**
     * Calculates the bond equivalent yield.
     *
     * @param faceValue              the amount paid at maturity
     * @param priceAmount            the amount originally paid
     * @param numberOfDaysToMaturity days to maturity
     * @return the bond equivalent yield
     */
    public static BigDecimal calculate(MonetaryAmount faceValue, MonetaryAmount priceAmount, int numberOfDaysToMaturity) {
        BigDecimal face = BigDecimal.valueOf(faceValue.getNumber().doubleValueExact());
        BigDecimal price = BigDecimal.valueOf(priceAmount.getNumber().doubleValueExact());
        BigDecimal returnOnInvestment = (face.subtract(price)).divide(price, MathContext.DECIMAL64);
        BigDecimal maturity = BigDecimal.valueOf(365).divide(BigDecimal.valueOf(numberOfDaysToMaturity), MathContext.DECIMAL64);

        return returnOnInvestment.multiply(maturity);
    }
}
