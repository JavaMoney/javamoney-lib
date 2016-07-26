package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Zero%20Coupon%20Bond%201.gif" />
 * <p> A zero coupon bond, sometimes referred to as a pure discount bond or simply discount bond, is a bond that does not pay coupon payments and instead pays one lump sum at maturity. The amount paid at maturity is called the face value. The term discount bond is used to reference how it is sold originally at a discount from its face value instead of standard pricing with periodic dividend payments as seen otherwise.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Zero_Coupon_Bond_Value.html
 */
public class ZeroCouponBondValue {

    /**
     * Private constructor.
     */
    private ZeroCouponBondValue() {
    }

    /**
     * Calculates the zero coupon bond value.
     *
     * @param face                    the face value of the bond
     * @param rate                    the rate
     * @param numberOfYearsToMaturity the number of years to maturity
     * @return the zero coupon bond value
     */
    public static MonetaryAmount calculate(MonetaryAmount face, Rate rate, int numberOfYearsToMaturity) {
        return face.divide(BigDecimal.ONE.add(rate.get()).pow(numberOfYearsToMaturity));
    }
}
