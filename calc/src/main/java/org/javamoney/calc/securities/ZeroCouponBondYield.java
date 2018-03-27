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
 * <img src="http://www.financeformulas.net/formulaimages/Zero%20Coupon%20Bond%20Yield%201.gif" />
 * <p> The zero coupon bond effective yield formula is used to calculate the periodic return for a zero coupon bond, or sometimes referred to as a discount bond.
 * <p> A zero coupon bond is a bond that does not pay dividends (coupons) per period, but instead is sold at a discount from the face value. For example, an investor purchases one of these bonds at $500, which has a face value at maturity of $1,000. Although no coupons are paid periodically, the investor will receive the return upon sell assuming that the rates remain constant or upon maturity.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Zero-Coupon-Bond-Effective-Yield.html
 */
public class ZeroCouponBondYield {
    /**
     * Private constructor.
     */
    private ZeroCouponBondYield() {
    }

    /**
     * Calculates the zero coupon bond yield.
     *
     * @param faceAmount      the face amount of the bond
     * @param presentAmount   the present amount
     * @param numberOfPeriods the number of periods
     * @return the zero coupon bond yield
     */
    public static double calculate(MonetaryAmount faceAmount, MonetaryAmount presentAmount, int numberOfPeriods) {
        final BigDecimal faceValue = BigDecimal.valueOf(faceAmount.getNumber().doubleValueExact());
        final BigDecimal presentValue = BigDecimal.valueOf(presentAmount.getNumber().doubleValueExact());
        final double fraction = faceValue.divide(presentValue, MathContext.DECIMAL64).doubleValue();

        return Math.pow(fraction, 1 / (double) numberOfPeriods) - 1;
    }
}
