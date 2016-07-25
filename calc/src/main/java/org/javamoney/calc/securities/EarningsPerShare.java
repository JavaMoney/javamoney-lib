package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Earnings%20Per%20Share%201.gif" />
 * <p>
 * <p> The formula for earnings per share, or EPS, is a company's net income expressed on a per share basis. Net income for a particular company can be found
 * on its income statement. It is important to note that the earnings per share formula only references common stock and any preferred stock dividends is
 * subtracted from the net income, if applicable.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Earnings_Per_Share.html
 */
public class EarningsPerShare {
    /**
     * Private constructor.
     */
    private EarningsPerShare() {
    }

    /**
     * Calculates the earnings per share.
     *
     * @param netIncome the company's net income
     * @param weightedAverageOfOutstandingShares the weighted average of outstanding shares of common stock
     * @return the earnings per share
     */
    public static MonetaryAmount calculate(MonetaryAmount netIncome, double weightedAverageOfOutstandingShares) {
        return netIncome.divide(weightedAverageOfOutstandingShares);
    }
}
