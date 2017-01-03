package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Price%20to%20Book%20Value%201.gif" />
 * <p>The Price to Book Ratio formula, sometimes referred to as the market to book ratio, is used to compare a company's net assets available to common shareholders relative to the sale price of its stock. The formula for price to book value is the stock price per share divided by the book value per share.
 * The stock price per share can be found as the amount listed as such through the secondary stock market.
 * <p>
 * The book value per share is considered to be the total equity for common stockholders which can be found on a company's balance sheet.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Price_to_Book_Value.html
 */
public class PriceToBookValue {

    /**
     * Private constructor.
     */
    private PriceToBookValue() {
    }

    /**
     * Calculates the price to book value.
     *
     * @param marketPricePerShare the market price per share
     * @param bookValuePerShare   the book value per share
     * @return the price to book value
     */
    public static BigDecimal calculate(MonetaryAmount marketPricePerShare, MonetaryAmount bookValuePerShare) {
        BigDecimal marketPricePerShareValue = BigDecimal.valueOf(marketPricePerShare.getNumber().doubleValueExact());
        BigDecimal bookValuePerShareValue = BigDecimal.valueOf(bookValuePerShare.getNumber().doubleValueExact());

        return marketPricePerShareValue.divide(bookValuePerShareValue, MathContext.DECIMAL64);
    }
}
