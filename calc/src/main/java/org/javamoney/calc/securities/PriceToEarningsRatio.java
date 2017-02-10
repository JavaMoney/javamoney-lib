package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Price%20Earnings%20Ratio%201.gif" />
 * <p> The formula for the price to earnings ratio, also referred to as the P/E Ratio, is the price per share divided by earnings per share. The price to earnings ratio is used as a quick calculation for how a company's stock is perceived by the market to be worth relative to the company's earnings. A higher price to earnings ratio implies that the market values the stock as a better investment than if there was a lower price to earnings ratio, ceteris paribus. The increased perceived worth is due to news, speculation, or analysis from investors that the stock has a higher growth potential for the future.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Price_Earnings_Ratio.html
 */
public class PriceToEarningsRatio {

    /**
     * Private constructor.
     */
    private PriceToEarningsRatio() {
    }

    /**
     * Calculates the price to earnings ratio.
     *
     * @param pricePerShare    the price per share
     * @param earningsPerShare the earnings per share
     * @return the price to earnings ratio
     */
    public static BigDecimal calculate(MonetaryAmount pricePerShare, MonetaryAmount earningsPerShare) {
        BigDecimal pricePerShareValue = BigDecimal.valueOf(pricePerShare.getNumber().doubleValueExact());
        BigDecimal earningsPerShareValue = BigDecimal.valueOf(earningsPerShare.getNumber().doubleValueExact());

        return pricePerShareValue.divide(earningsPerShareValue, MathContext.DECIMAL64);
    }
}
