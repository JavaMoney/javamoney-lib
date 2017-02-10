package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Capital%20Gains%20Yield%201.gif" />
 * <p>
 * The formula for the capital gains yield is used to calculate the return on a stock based solely on the appreciation of the stock. The formula for capital
 * gains yield does not include dividends paid on the stock, which can be found using the dividend yield. The capital gains yield and dividend yield is
 * combined to calculate the total stock return.
 * <p>
 * The capital gains yield formula uses the rate of change formula. Calculating the capital gains yield is effectively calculating the rate of change of the
 * stock price. The rate of change can be found by subtracting an ending amount from the original amount then divided by the original amount.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Capital-Gains-Yield.html
 */
public class CapitalGainsYield {

    /**
     * Private constructor.
     */
    private CapitalGainsYield() {
    }

    /**
     * Calculates the return on a stock based solely on the appreciation of the stock.
     *
     * @param initialStockPrice          initial stock price
     * @param stockPriceAfterFirstPeriod stock price after first period
     * @return the capital gains yield
     */
    public static BigDecimal calculate(MonetaryAmount initialStockPrice, MonetaryAmount stockPriceAfterFirstPeriod) {
        BigDecimal initialStockPriceValue = new BigDecimal(initialStockPrice.getNumber().doubleValueExact());
        BigDecimal stockValueAfterFirstPeriod = new BigDecimal(stockPriceAfterFirstPeriod.getNumber().doubleValueExact());

        return stockValueAfterFirstPeriod.subtract(initialStockPriceValue).divide(initialStockPriceValue, MathContext.DECIMAL64);
    }
}
