package org.javamoney.calc.securities;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Total%20Stock%20Return%201.gif" />
 * <p> The formula for the total stock return is the appreciation in the price plus any dividends paid, divided by the original price of the stock. The income sources from a stock is dividends and its increase in value. The first portion of the numerator of the total stock return formula looks at how much the value has increased (P1 - P0). The denominator of the formula to calculate a stock's total return is the original price of the stock which is used due to being the original amount invested.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Total-Stock-Return.html
 */
public class TotalStockReturn {
    /**
     * Private constructor.
     */
    private TotalStockReturn() {
    }

    /**
     * Calculates the total stock return.
     *
     * @param initialPrice the initial stock price
     * @param endingPrice  the ending stock price
     * @param dividends    the dividends
     * @return the total stock return
     */
    public static BigDecimal calculate(MonetaryAmount initialPrice, MonetaryAmount endingPrice, MonetaryAmount dividends) {
        BigDecimal initialPriceValue = BigDecimal.valueOf(initialPrice.getNumber().doubleValueExact());
        BigDecimal endingPriceValue = BigDecimal.valueOf(endingPrice.getNumber().doubleValueExact());
        BigDecimal dividendsValue = BigDecimal.valueOf(dividends.getNumber().doubleValueExact());

        return (endingPriceValue.subtract(initialPriceValue).add(dividendsValue)).divide(initialPriceValue, MathContext.DECIMAL64);
    }
}
