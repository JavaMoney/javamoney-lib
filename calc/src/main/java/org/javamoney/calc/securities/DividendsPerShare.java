package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Dividends%20Per%20Share%201.gif" />
 * <p>
 * <p> The formula for dividends per share, or DPS, is the annual dividends paid divided by the number of shares outstanding.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Dividends_Per_Share.html
 */
public class DividendsPerShare {

    /**
     * Private constructor.
     */
    private DividendsPerShare() {
    }

    /**
     * Calculates the dividends per share.
     *
     * @param dividends      the annual dividends paid
     * @param numberOfShares the number of shares outstanding
     * @return the dividends per share
     */
    public static MonetaryAmount calculate(MonetaryAmount dividends, double numberOfShares) {
        return dividends.divide(numberOfShares);
    }
}
