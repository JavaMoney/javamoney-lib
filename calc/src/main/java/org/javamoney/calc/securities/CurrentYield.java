package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Current%20Yield%201.gif" />
 * <p>
 * <p> Current yield is a bond's annual return based on its annual coupon payments and current price (as opposed to its original price or face).
 * The formula for current yield is a bond's annual coupons divided by its current price.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Current-Yield.html
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
