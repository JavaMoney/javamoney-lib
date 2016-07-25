package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Price%20to%20Sales%201.gif" />
 * <p> The formula for price to sales ratio, sometimes referenced as the P/S Ratio, is the perceived value of a stock by the market compared to the revenues of the company. The price to sales ratio is calculated by dividing the stock price by sales per share. Sales per share uses the weighted average of shares for the time period evaluated, which is generally one year.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Price-to-Sales-Ratio.html
 */
public class PriceToSalesRatio {

    /**
     * Private constructor.
     */
    private PriceToSalesRatio() {
    }

    /**
     * Calculates the price to sales ratio.
     *
     * @param sharePrice    the share price
     * @param salesPerShare the sales per share
     * @return the price to sales ratio
     */
    public static BigDecimal calculate(MonetaryAmount sharePrice, MonetaryAmount salesPerShare) {
        BigDecimal sharePriceValue = BigDecimal.valueOf(sharePrice.getNumber().doubleValueExact());
        BigDecimal salesPerShareValue = BigDecimal.valueOf(salesPerShare.getNumber().doubleValueExact());

        return sharePriceValue.divide(salesPerShareValue, MathContext.DECIMAL64);
    }
}
