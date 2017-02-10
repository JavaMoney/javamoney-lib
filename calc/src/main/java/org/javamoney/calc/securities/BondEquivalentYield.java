package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Bond%20Equivalent%20Yield.gif" />
 * <p> The bond equivalent yield formula is used to determine the annual yield on a discount, or zero coupon, bond.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Bond_Equivalent_Yield.html
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
        BigDecimal face = new BigDecimal(faceValue.getNumber().doubleValueExact());
        BigDecimal price = new BigDecimal(priceAmount.getNumber().doubleValueExact());
        BigDecimal returnOnInvestment = (face.subtract(price)).divide(price, MathContext.DECIMAL64);
        BigDecimal maturity = BigDecimal.valueOf(365).divide(BigDecimal.valueOf(numberOfDaysToMaturity), MathContext.DECIMAL64);

        return returnOnInvestment.multiply(maturity);
    }
}
