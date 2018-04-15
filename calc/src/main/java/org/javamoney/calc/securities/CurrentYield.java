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
 * Current yield is a bond's annual return based on its annual coupon payments and current price (as opposed to its original price or face).
 * The formula for current yield is a bond's annual coupons divided by its current price.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Current-Yield.html">http://www.financeformulas.net/Current-Yield.html</a>
 */
public class CurrentYield {

    /**
     * Private constructor.
     */
    private CurrentYield() {
    }

    /**
     * Calculates the current yield.
     *
     * @param annualCoupons    the bond's annual coupons
     * @param currentBondPrice the current bond price
     * @return the current yield
     */
    public static BigDecimal calculate(MonetaryAmount annualCoupons, MonetaryAmount currentBondPrice) {
        BigDecimal annualCouponsValue = BigDecimal.valueOf(annualCoupons.getNumber().doubleValueExact());
        BigDecimal currentBondPriceValue = BigDecimal.valueOf(currentBondPrice.getNumber().doubleValueExact());

        return annualCouponsValue.divide(currentBondPriceValue, MathContext.DECIMAL64);
    }
}
