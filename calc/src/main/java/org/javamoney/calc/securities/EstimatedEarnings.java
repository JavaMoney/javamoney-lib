package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Estimated%20Earnings%201.gif" />
 * <p>
 * <img src="http://www.financeformulas.net/formulaimages/Estimated%20Earnings%202.gif" />
 * <p>
 * <p> The formula for estimated earnings is forecasted sales minus forecasted expenses.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Estimated-Earnings.html
 */
public class EstimatedEarnings {

    /**
     * Private constructor.
     */
    private EstimatedEarnings() {
    }

    /**
     * Calculates the estimated earnings.
     *
     * @param forecastedSales    the forecasted sales
     * @param forecastedExpenses the forecasted expenses
     * @return the estimated earnings
     */
    public static MonetaryAmount calculate(MonetaryAmount forecastedSales, MonetaryAmount forecastedExpenses) {
        return forecastedSales.subtract(forecastedExpenses);
    }

    /**
     * Calculates the estimated earnings.
     *
     * @param projectedSales           the projected sales
     * @param projectedNetProfitMargin the projected net profit margin
     * @return the estimated earnings
     */
    public static MonetaryAmount calculate(MonetaryAmount projectedSales, BigDecimal projectedNetProfitMargin) {
        return projectedSales.multiply(projectedNetProfitMargin);
    }
}
