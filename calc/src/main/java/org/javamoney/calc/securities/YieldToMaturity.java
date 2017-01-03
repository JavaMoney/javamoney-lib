package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Yield%20to%20Maturity%201.gif" />
 * <p>The yield to maturity formula is used to calculate the yield on a bond based on its current price on the market. The yield to maturity formula looks
 * at the effective yield of a bond based on compounding as opposed to the simple yield which is found using the dividend yield formula.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Yield_to_Maturity.html
 */
public class YieldToMaturity {

    /**
     * Private constructor.
     */
    private YieldToMaturity() {
    }

    /**
     * Calculates the yield to maturity.
     *
     * @param couponPaymentAmount     the coupon/interest payment
     * @param faceAmount              the face value
     * @param priceAmount             the price
     * @param numberOfYearsToMaturity the number of years to maturity
     * @return the yield to maturity
     */
    public static BigDecimal calculate(MonetaryAmount couponPaymentAmount, MonetaryAmount faceAmount, MonetaryAmount priceAmount, int numberOfYearsToMaturity) {
        final BigDecimal coupon = new BigDecimal(couponPaymentAmount.getNumber().doubleValueExact());
        final BigDecimal face = new BigDecimal(faceAmount.getNumber().doubleValueExact());
        final BigDecimal price = new BigDecimal(priceAmount.getNumber().doubleValueExact());

        final BigDecimal averagedDifference = face.subtract(price).divide(BigDecimal.valueOf(numberOfYearsToMaturity), MathContext.DECIMAL64);
        final BigDecimal averagePrice = face.add(price).divide(BigDecimal.valueOf(2), MathContext.DECIMAL64);

        return coupon.add(averagedDifference).divide(averagePrice, MathContext.DECIMAL64);
    }
}
